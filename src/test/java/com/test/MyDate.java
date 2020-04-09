package com.test;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


    public class MyDate {

        public static void main(String[] args) throws ParseException{
        	String value = "{{username}} sdfjkj safdlkjk asdflkj l {{password}} skaldfj sadflkj";
        	List<String> finalParams = new ArrayList<String>();
        	String[] array = value.split(" ");
        	for(String s : array) {
        		String result = "";
        		if(s.startsWith("{{")){
        			for(int i=2;i<s.length();i++) {
        				if(s.charAt(i) == '}')
        					continue;
        				result += s.charAt(i);
        			}
        		}
        		System.out.println("result = "+result);
        		if(!result.isEmpty())
        			finalParams.add(result);
        	}
        	System.out.println("final Params = "+finalParams);
		/*
		 * DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); Date currentDate
		 * = new Date(); List<String> lastOneWeekDates = new ArrayList<String>(); String
		 * dates; for (int i = 0; i < 7; i++) { SimpleDateFormat dateFormat1 = new
		 * SimpleDateFormat("yyyy-MM-dd"); Date startDate =
		 * dateFormat1.parse(dateFormat.format(currentDate)); Calendar cal =
		 * Calendar.getInstance(); cal.setTime(startDate); cal.add(Calendar.DATE, i *
		 * -1); dateFormat1.format(cal.getTime()); dates =
		 * dateFormat1.format(cal.getTime()); lastOneWeekDates.add(dates); }
		 * System.out.println(lastOneWeekDates);
		 */
            //System.out.println(getAllDatesBetweenTwoDates("2015/09/27","2015/10/05","yyyy/MM/dd","dd-MM-yyyy",false));
        }
        public static List<String> getAllDatesBetweenTwoDates(String stdate,String enddate,String givenformat,String resultformat,boolean onlybunessdays) throws ParseException{
            DateFormat sdf;
            DateFormat sdf1;
            List<Date> dates = new ArrayList<Date>();
            List<String> dateList = new ArrayList<String>();
              SimpleDateFormat checkformat = new SimpleDateFormat(resultformat); 
              checkformat.applyPattern("EEE");  // to get Day of week
            try{
                sdf = new SimpleDateFormat(givenformat);
                sdf1 = new SimpleDateFormat(resultformat);
                stdate=sdf1.format(sdf.parse(stdate));
                enddate=sdf1.format(sdf.parse(enddate));

                Date  startDate = (Date)sdf1.parse( stdate); 
                Date  endDate = (Date)sdf1.parse( enddate);
                long interval = 24*1000 * 60 * 60; // 1 hour in millis
                long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
                long curTime = startDate.getTime();
                while (curTime <= endTime) {
                    dates.add(new Date(curTime));
                    curTime += interval;
                }
                for(int i=0;i<dates.size();i++){
                    Date lDate =(Date)dates.get(i);
                    String ds = sdf1.format(lDate);   
                    if(onlybunessdays){
                        String day= checkformat.format(lDate); 
                        if(!day.equalsIgnoreCase("Sat") && !day.equalsIgnoreCase("Sun")){
                              dateList.add(ds);
                        }
                    }else{
                          dateList.add(ds);
                    }

                    //System.out.println(" Date is ..." + ds);

                }


            }catch(ParseException e){
                e.printStackTrace();
                throw e;
            }finally{
                sdf=null;
                sdf1=null;
            }
            return dateList;
        }
    }