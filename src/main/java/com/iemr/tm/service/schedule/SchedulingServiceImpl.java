package com.iemr.tm.service.schedule;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.tm.data.schedule.Slot;
import com.iemr.tm.data.schedule.SpecialistAvailability;
import com.iemr.tm.data.schedule.SpecialistAvailabilityDetail;
import com.iemr.tm.data.schedule.SpecialistInput2;
import com.iemr.tm.data.specialist.Specialist;
import com.iemr.tm.repo.schedule.SpecialistAvailabilityDetailRepo;
import com.iemr.tm.repo.schedule.SpecialistAvailabilityRepo;
import com.iemr.tm.service.specialist.SpecialistService;
import com.iemr.tm.utils.config.ConfigProperties;
import com.iemr.tm.utils.exception.TMException;

@Service
public class SchedulingServiceImpl implements SchedulingService {

	// slot size in mins multiple of 5
	final static Integer slotsize = Integer.parseInt(ConfigProperties.getPropertyByName("scheduling-slotsize"));
	final static Integer totalslotsize = 24 * 60 / 5;
	@Autowired
	SpecialistAvailabilityDetailRepo specialistAvailabilityDetailRepo;
	@Autowired
	SpecialistAvailabilityRepo specialistAvailabilityRepo;

	@Autowired
	private SpecialistService specialistService;

	final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private String initializeString() {
		// Each slot is of 5 mins so one hour has 12 slots and day has 12*24
		// U - Unavailable doctor is not on duty hours
		// A - Available doctor is on duty hours
		// B - Booked slot for doctor
		StringBuilder slot = new StringBuilder();

		LongStream.rangeClosed(1, 12 * 24L).forEach(e -> {
			slot.append('U');
		});

		return slot.toString();

	}

	private static String updateString(String slotdetail, Timestamp startTime, Timestamp endTime, char status)
			throws TMException {
		// U - Unavailable doctor is not on duty hours
		// A - Available doctor is on duty hours
		// B - Booked slot for doctor
		// C - Cancel appointment
		Integer startslot = ((startTime.getHours() * 60) + (startTime.getMinutes())) / 5;
		Integer endslot = ((endTime.getHours() * 60) + (endTime.getMinutes())) / 5;
		// Integer startslot=
		// Integer endslot = startslot + (durationSlot / 5);
		String currentstatus = slotdetail.substring(startslot, endslot);

		StringBuilder slotfinal = new StringBuilder();
		switch (status) {
		case 'A':
			currentstatus = currentstatus.replace('U', 'A');
			break;
		case 'B':
			if (currentstatus.indexOf('B') != -1)
				throw new TMException("Already Booked Slot");
			if (currentstatus.indexOf('U') != -1)
				throw new TMException("Specialist unavailable at that time");
			currentstatus = currentstatus.replace('A', 'B');
			break;
		case 'U':
			if (currentstatus.indexOf('B') != -1)
				throw new TMException("Appointment Booked. Please Cancel the Consultation first");
			currentstatus = currentstatus.replace('A', 'U');
			break;
		case 'C':
			currentstatus = currentstatus.replace('B', 'A');
			break;
		}

		if (startslot > 0) {
			slotfinal.append(slotdetail.substring(0, startslot));
		}
		slotfinal.append(currentstatus);
		if (endslot < slotdetail.length()) {
			slotfinal.append(slotdetail.substring(endslot, slotdetail.length()));
		}
		return slotfinal.toString();

	}

	@Override
	public SpecialistAvailabilityDetail markAvailability(SpecialistAvailabilityDetail specialistInput)
			throws TMException {
		// TODO Auto-generated method stub
		// First get configure slot from t_SpecialistAvailability for the date
		// range and user
		// HashMap or Map it with date
		// update if date there otherwise just insert

		logger.info("specialistInput result" + specialistInput.toString());
		Date temp = specialistInput.getConfiguredFromDate();
		final Date startDate = new Date(temp.getYear(), temp.getMonth(), temp.getDate());
		// startDate.setHours(0);
		// startDate.setMinutes(0);
		// startDate.setSeconds(0);

		Date temp1 = specialistInput.getConfiguredToDate();
		final Date endDate = new Date(temp1.getYear(), temp1.getMonth(), temp1.getDate());
		final String createdBy = specialistInput.getCreatedBy();
		final Long userID = specialistInput.getUserID();

		Integer durationDays = (int) ((endDate.getTime() - startDate.getTime()) / 86400000);
		// endDate.setHours(0);
		// endDate.setMinutes(0);
		// endDate.setSeconds(0);

		Timestamp startTime = specialistInput.getConfiguredFromTime();
		Timestamp endTime = specialistInput.getConfiguredToTime();

		System.out.println(startDate.getTimezoneOffset());

		specialistInput.setIsAvailability(true);

		List<SpecialistAvailability> slotdetails = specialistAvailabilityRepo
				.findByConfiguredDateBetweenAndUserID(startDate, endDate, specialistInput.getUserID());
		Map<Date, SpecialistAvailability> result = slotdetails.stream()
				.collect(Collectors.toMap(slot -> slot.getConfiguredDate(), slot -> slot));
		System.out.println(specialistInput.getConfiguredFromDate());
		System.out.println(specialistInput.getConfiguredToDate());
		System.out.println(specialistInput);
		logger.info("REsult from DB result" + result.toString());
		List<SpecialistAvailability> output = new ArrayList<>();
		logger.info(startDate.getTime() + "");
		logger.info(endDate.getTime() + "");
		// for(Date i=startDate;i.getTime()<=
		// endDate.getTime();i.setDate(i.getDate()+1)){
		// System.out.println(i);
		// SpecialistAvailability now=result.get(i);
		// if(now==null){
		// now=new SpecialistAvailability();
		// now.setUserID(specialistInput.getUserID());
		// now.setConfiguredDate((Date) i.clone());
		// now.setTimeSlot(initializeString());
		// now.setCreatedBy(specialistInput.getCreatedBy());
		// }else{
		// now.setModifiedBy(specialistInput.getCreatedBy());
		// }
		// now.setTimeSlot(updateString(now.getTimeSlot(), startTime, endTime,
		// 'A'));
		// output.add(now);
		// logger.info("printintg"+now.toString());
		// }

		List<Integer> excludedays = specialistInput.getExcludeDays();
		IntStream.rangeClosed(0, durationDays).forEach(e -> {

			try {
				Date i = new Date(startDate.getTime());
				i.setDate(i.getDate() + e);
				Integer day = i.getDay();
				if (!(excludedays != null && excludedays.size() > 0 && excludedays.contains(day))) {
					System.out.println(i);
					SpecialistAvailability now = result.get(i);
					if (now == null) {
						now = new SpecialistAvailability();
						now.setUserID(userID);
						now.setConfiguredDate((Date) i.clone());
						now.setTimeSlot(initializeString());
						now.setCreatedBy(createdBy);
					} else {
						now.setModifiedBy(createdBy);
					}
					now.setTimeSlot(updateString(now.getTimeSlot(), startTime, endTime, 'A'));

					output.add(now);
					logger.info("printintg" + now.toString());
				}

			} catch (TMException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// System.out.println(output);
		logger.info("output result" + output.toString());
		specialistAvailabilityRepo.save(output);
		specialistInput = specialistAvailabilityDetailRepo.save(specialistInput);

		return specialistInput;

	}

	@Override
	public SpecialistAvailabilityDetail markUnavailability(SpecialistAvailabilityDetail specialistInput)
			throws TMException {
		// TODO Auto-generated method stub
		Date temp = specialistInput.getConfiguredFromDate();
		Date startDate = new Date(temp.getYear(), temp.getMonth(), temp.getDate());

		Timestamp startTime = specialistInput.getConfiguredFromTime();
		Timestamp endTime = specialistInput.getConfiguredToTime();

		System.out.println(startDate.getTimezoneOffset());

		specialistInput.setIsAvailability(false);

		SpecialistAvailability slotdetails = specialistAvailabilityRepo.findOneByConfiguredDateAndUserID(startDate,
				specialistInput.getUserID());

		System.out.println(specialistInput.getConfiguredFromDate());
		System.out.println(specialistInput.getConfiguredToDate());
		System.out.println(specialistInput);

		// logger.info("REsult from DB result" + slotdetails.toString());
		List<SpecialistAvailability> output = new ArrayList<>();
		logger.info(startDate.getTime() + "");
		// logger.info(endDate.getTime()+"");
		// System.out.println(i);
		SpecialistAvailability now = slotdetails;
		if (slotdetails == null) {
			now = new SpecialistAvailability();
			now.setUserID(specialistInput.getUserID());
			now.setConfiguredDate(startDate);
			now.setTimeSlot(initializeString());
			now.setCreatedBy(specialistInput.getCreatedBy());
		} else {
			now.setModifiedBy(specialistInput.getCreatedBy());
		}
		now.setTimeSlot(updateString(now.getTimeSlot(), startTime, endTime, 'U'));
		output.add(now);
		logger.info("printintg" + now.toString());

		// System.out.println(output);
		logger.info("output result" + output.toString());
		specialistAvailabilityRepo.save(output);
		specialistInput = specialistAvailabilityDetailRepo.save(specialistInput);

		return specialistInput;

	}

	@Override
	public SpecialistAvailability fetchavailability(SpecialistInput2 specialistInput) {
		// TODO Auto-generated method stub
		SpecialistAvailability slotdetails = specialistAvailabilityRepo
				.findOneByConfiguredDateAndUserID(specialistInput.getDate(), specialistInput.getUserID());
		if (slotdetails == null) {
			slotdetails = new SpecialistAvailability(specialistInput.getUserID(), specialistInput.getDate(),
					new ArrayList<Slot>());
		} else {
			String slot = slotdetails.getTimeSlot();

			slotdetails.setSlots(getslotsplit(slot));

		}

		return slotdetails;
	}

	public List<Slot> getslotsplit(String slot) {

		Integer size = slotsize / 5;
		// String[] arrayslot=slot.split("[A-Z]{"+size+"}");
		List<Slot> slota = new ArrayList<Slot>();

		StringBuilder slotstring = new StringBuilder();
		StringBuilder bookedslotstring = new StringBuilder();

		LongStream.rangeClosed(1, size).forEach(e -> {
			slotstring.append('A');
			bookedslotstring.append('B');
		});

		String basicslot = slotstring.toString();
		String bookedslotString = bookedslotstring.toString();

		for (int i = 0; i < totalslotsize; i = i + size) {
			slota.add(new Slot(i, size, slot.substring(i, i + size), basicslot, bookedslotString));
		}
		return slota;
	}

	@Override
	public String bookSlot(SpecialistInput2 specialistInput, char status) throws TMException {
		// TODO Auto-generated method stub
		Date temp = specialistInput.getDate();
		Date startDate = new Date(temp.getYear(), temp.getMonth(), temp.getDate());

		SpecialistAvailability slotdetails = specialistAvailabilityRepo.findOneByConfiguredDateAndUserID(startDate,
				specialistInput.getUserID());

		if (slotdetails == null) {
			throw new TMException("Doctor not available");
		}

		LocalTime s = specialistInput.getFromTime();
		LocalTime e = specialistInput.getToTime();

		Timestamp startTime = new Timestamp(0, 0, 0, s.getHour(), s.getMinute(), s.getSecond(), 0);
		Timestamp endTime = new Timestamp(0, 0, 0, e.getHour(), e.getMinute(), e.getSecond(), 0);

		String updatedString = updateString(slotdetails.getTimeSlot(), startTime, endTime, status);

		slotdetails.setTimeSlot(updatedString);
		if (specialistInput.getModifiedBy() != null) {
			slotdetails.setModifiedBy(specialistInput.getModifiedBy());
		}

		specialistAvailabilityRepo.save(slotdetails);

		return "Booked";
	}

	@Override
	public List<SpecialistAvailability> fetchmonthavailability(SpecialistInput2 specialistInput, Integer year,
			Integer month, Integer day) {
		// TODO Auto-generated method stub
		List<SpecialistAvailability> slotdetails = specialistAvailabilityRepo.findByMonthAndUserID(day, month, year,
				specialistInput.getUserID());
		String pattern = "([A]+)|([B]+)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);
		if (slotdetails.size() > 0) {

			slotdetails.parallelStream().forEach(action -> {
				List<Slot> slots = new ArrayList<Slot>();
				String line = action.getTimeSlot();
				// Now create matcher object.
				Matcher m = r.matcher(line);
				int count = 0;

				while (m.find()) {
					slots.add(new Slot(m.start(), m.end(), m.group()));
				}

				action.setSlots(slots);
			});
		}

		return slotdetails;
	}

	@Override
	public List<Specialist> fetchAllAvailability(SpecialistInput2 specialistInput) throws TMException {
		// TODO Auto-generated method stub
		List<SpecialistAvailability> listspec = new ArrayList<>();
		List<Specialist> user = specialistService.getspecialistUser(specialistInput.getProviderServiceMapID(),
				specialistInput.getSpecializationID(), specialistInput.getUserID());
		if (user != null && user.size() > 0) {
			List<Long> userids = new ArrayList<>();
			user.forEach(action -> {
				userids.add(action.getUserID());
			});
			List<SpecialistAvailability> slotdetails = specialistAvailabilityRepo
					.findByConfiguredDateAndUserIDIn(specialistInput.getDate(), userids);

			Map<Long, SpecialistAvailability> map = slotdetails.stream()
					.collect(Collectors.toMap(SpecialistAvailability::getUserID, item -> item));

			user.parallelStream().forEach(action -> {
				SpecialistAvailability temp = map.get(action.getUserID());
				if (temp != null && temp.getTimeSlot() != null) {
					temp.setSlots(getslotsplit(temp.getTimeSlot()));
				}
				action.setSpecialistAvailability(temp);
			});

		}
		return user;
	}

}
