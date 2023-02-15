package com.ssafy.api.service;

import com.ssafy.api.request.AttendReq;
import com.ssafy.api.request.MeetCreateReq;
import com.ssafy.api.request.MeetEndReq;
import com.ssafy.api.request.SttReq;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import io.swagger.models.Model;
import io.swagger.models.auth.In;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("meetService")
public class MeetServiceImpl implements MeetService {
    @Autowired
    UserRepositorySupport userRepositorySupport;
    @Autowired
    MeetRepository meetRepository;
    @Autowired
    GroupRepositorySupport groupRepositorySupport;
    @Autowired
    private User_MeetRepository user_MeetRepository;
    @Autowired
    private User_GroupRepository user_GroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private Group_MeetRepository group_MeetRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public boolean createMeet(int userId, MeetCreateReq createReq) {
        Meet meet = new Meet();
        User user = userRepository.findUserById(userId);
        Group gp = groupRepository.findGroupById(createReq.getGroupid());
        System.out.println(gp);
        if (gp != null) {
            if (meetRepository.findMeetByTitle(createReq.getTitle()) != null) return false;

            meet.setTitle(createReq.getTitle());
            meet.setDate(createReq.getDate());
            meet.setStarttime(createReq.getStarttime());
            meet.setEndtime(createReq.getEndtime());
            meet.setUserid(user);
            meet.setGroupid(gp);
            List<User_Group> ug = user_GroupRepository.findByGroupid(gp);
            List<Integer> users = new ArrayList<>();

            createReq.setUsers(users);
            meetRepository.save(meet);


            Group_Meet gm = new Group_Meet();
            gm.setGroupid(gp);
            gm.setMeetid(meetRepository.findMeetByTitle(createReq.getTitle()));

            group_MeetRepository.save(gm);
            insertMeetMember(userId, createReq);
            for (int i = 0; i < ug.size(); i++) {
                int getId = ug.get(i).getUserid().getId();
                Attendance attendance = new Attendance();
                attendance.setDate(meet.getDate());
                attendance.setUserid(ug.get(i).getUserid());
                attendance.setGroupid(gp);
                attendance.setMeetid(meet);
                attendanceRepository.save(attendance);
                users.add(getId);
            }

            return true;
        } else {
            if (meetRepository.findMeetByTitle(createReq.getTitle()) != null) return false;
            meet.setTitle(createReq.getTitle());
            meet.setDate(createReq.getDate());
            meet.setStarttime(createReq.getStarttime());
            meet.setEndtime(createReq.getEndtime());
            meet.setUserid(user);

            meetRepository.save(meet);
            insertMeetMember(userId, createReq);
            return true;
        }
    }

    @Override
    public void insertMeetMember(int userId, MeetCreateReq createReq) {
        User user = userRepository.findUserById(userId);
        Meet meet = meetRepository.findMeetByTitle(createReq.getTitle());
        Group group = groupRepository.findGroupById(createReq.getGroupid());
        if (group != null) {
            List<User_Group> ugs = user_GroupRepository.findByGroupid(group);
            for (int i = 0; i < ugs.size(); i++) {
                User_Meet um = new User_Meet();
                um.setMeetid(meet);
                um.setUserid(ugs.get(i).getUserid());
                user_MeetRepository.save(um);
            }

        }
        if (user.getId() == meet.getUserid().getId()) {
            List<Integer> users = createReq.getUsers();
            for (int i = 0; i < users.size(); i++) {
                User_Meet um = new User_Meet();
                User addUser = userRepository.findUserById(users.get(i));
                um.setMeetid(meet);
                um.setUserid(addUser);
                user_MeetRepository.save(um);
            }
        }

    }

    @Transactional
    @Override
    public boolean endMeet(int meetId, MeetEndReq endReq) {
        Meet meet = meetRepository.findMeetById(meetId);
        List<Attendance> attendances = attendanceRepository.findAttendancesByMeetid(meet);
        if (meet == null) return false;
        if (endReq.getStt() != null) {

            String filePath = "/app/build/stt/";
            String fileName = meet.getTitle() + "note" + ".xlsx";
            meet.setEndtime(endReq.getEndtime());
            meet.setStt(fileName);
            meet.setVideo(endReq.getVideo());
            meetRepository.save(meet);
            if (meet.getGroupid() != null) {
                List<Attendance> atts = new ArrayList<>();

                for (int i = 0; i < attendances.size(); i++) {
                    User user = userRepository.findUserById(attendances.get(i).getUserid().getId());
                    Attendance attendance = attendanceRepository.findAttendanceByUseridAndMeetid(user, meet);
                    Integer count = 0;
                    if (attendance.getAttendcount() == null) {
                        attendance.setAttendance(0);
                        atts.add(attendance);
                    } else {
                        count = attendance.getAttendcount();
                        Time et = meet.getEndtime();
                        Time st = meet.getStarttime();
                        double countTime = ((et.getTime() - st.getTime()) / 1000);
                        double att = (count / countTime) * 100;
                        attendance.setAttendance(Math.ceil(att));
                        atts.add(attendance);
                    }
                }
                attendanceRepository.saveAll(atts);

            }

            SttReq sttReq = new SttReq();
            sttReq.setTitle(meet.getTitle());
            sttReq.setName(meet.getUserid().getName());
            sttReq.setDate(String.valueOf(meet.getDate()));
            sttReq.setGroupName(meet.getGroupid().getName());
            if(meet.getGroupid()==null){
                List<User_Meet> userMeet = user_MeetRepository.findByMeetid(meet);
                List<String> name = new ArrayList<>();
                for (int i = 0; i < userMeet.size(); i++) {
                    name.add(userMeet.get(i).getUserid().getName());
                }
                sttReq.setUserName(name);
            }else {
                List<User_Group> userGroup = user_GroupRepository.findByGroupid(meet.getGroupid());
                List<String> name = new ArrayList<>();
                for (int i = 0; i < userGroup.size(); i++) {
                    name.add(userGroup.get(i).getUserid().getName());
                }
                sttReq.setUserName(name);
            }
            sttReq.setStt(endReq.getStt());
            makeExcelFile(sttReq);
            return true;

        }
        meet.setEndtime(endReq.getEndtime());
        meet.setVideo(endReq.getVideo());
        meetRepository.save(meet);
        System.out.println("그룹아이디" + meet.getGroupid().getId());
        if (meet.getGroupid() != null) {
            List<Attendance> atts = new ArrayList<>();
            for (int i = 0; i < attendances.size(); i++) {
                User user = userRepository.findUserById(attendances.get(i).getUserid().getId());
                Attendance attendance = attendanceRepository.findAttendanceByUseridAndMeetid(user, meet);
                Integer count = 0;
                if (attendance.getAttendcount() == null) {
                    attendance.setAttendance(0);
                    atts.add(attendance);
                } else {
                    count = attendance.getAttendcount();
                    System.out.println(count);
                    Time et = meet.getEndtime();
                    Time st = meet.getStarttime();
                    System.out.println("et+" + et);
                    System.out.println("st+" + st);
                    double countTime = ((et.getTime() - st.getTime()) / 1000);
                    System.out.println(countTime);
                    double att = (count / countTime) * 100;
                    attendance.setAttendance(Math.ceil(att));
                    System.out.println(Math.ceil(att));
                    atts.add(attendance);
                }

            }
            attendanceRepository.saveAll(atts);
        }
        return true;
    }

    @Override
    public boolean recogtest(String[] stt) {
        if (stt == null) return false;
        String[] note = stt;
//           String[] note = {"김학철입니다.","반갑습니다."};
        String filePath = "/app/build/stt/";
        String fileName = "test" + UUID.randomUUID() + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(filePath + fileName);
            for (int i = 0; i < note.length; i++) {
                System.out.println(note[i]);
                fileWriter.write(note[i] + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public boolean addAttendance(int meetid, AttendReq attendReq) {
        Meet meet = meetRepository.findMeetById(meetid);
        User user = userRepository.findUserByName(attendReq.getUserId());
        Attendance attendance = attendanceRepository.findAttendanceByUseridAndMeetid(user, meet);
        if (attendance == null) return false;
        if (attendance.getAttendcount() == null) {

            attendance.setAttendcount(attendReq.getAttendcount());
            attendanceRepository.save(attendance);
            return true;
        }

        attendance.setAttendcount(attendance.getAttendcount() + attendReq.getAttendcount());
        attendanceRepository.save(attendance);
        return true;
    }

    @Override
    public void makeExcelFile(SttReq sttReq) {
//        String filePath = "/app/build/stt/";
//        String fileName = sttReq.getTitle() + "note" + ".xlsx";
//
        String filePath = "C:/Users/SSAFY/Pictures/meetnote/";
        String fileName = "test.xlsx";
        try {
            File file = new File(filePath + fileName);
            FileOutputStream fileout = new FileOutputStream(file);
            SXSSFWorkbook workbook = new SXSSFWorkbook();

            // 시트 생성
            SXSSFSheet sheet = workbook.createSheet("stt");

//            //시트 열 너비 설정
//            sheet.setColumnWidth(0, 1500);
//            sheet.setColumnWidth(0, 3000);
//            sheet.setColumnWidth(0, 3000);
//            sheet.setColumnWidth(0, 1500);


            //cellsttle 정렬
            CellStyle mergeRowStyle1 = workbook.createCellStyle();
            //가로
            mergeRowStyle1.setAlignment(HorizontalAlignment.CENTER);
            //세로
            mergeRowStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

            //cellstyle 테두리 border
            mergeRowStyle1.setBorderTop(BorderStyle.THICK);
            mergeRowStyle1.setBorderLeft(BorderStyle.THICK);
            mergeRowStyle1.setBorderBottom(BorderStyle.THICK);
            mergeRowStyle1.setBorderRight(BorderStyle.THICK);
            //cell font 폰트설정
            Font headerFont = workbook.createFont();
            headerFont.setFontName("나눔고딕");
            headerFont.setFontHeight((short) 700);
            headerFont.setBold(true);

            mergeRowStyle1.setFont(headerFont);

            // 셀 병합
            int rowLocation = 0;
            Row row = null;
            Cell cell = null;
            for (int j = 1; j < 4; j++) {
                row = sheet.createRow(j);
                //셀 병합 제목
                for (int i = 4; i < 10; i++) {
                    cell = row.createCell(i);
                    cell.setCellStyle(mergeRowStyle1);
                    cell.setCellValue(sttReq.getTitle());
                }
            }
            sheet.addMergedRegion(new CellRangeAddress(1, 3, 4, 9));

            //셀 병합 참조 데이터
            XSSFCellStyle mergeRowStyle2 = (XSSFCellStyle) workbook.createCellStyle();
            //가로
            mergeRowStyle2.setAlignment(HorizontalAlignment.CENTER);
            //세로
            mergeRowStyle2.setVerticalAlignment(VerticalAlignment.CENTER);

            //cellstyle 테두리 border
            mergeRowStyle2.setBorderTop(BorderStyle.THICK);
            mergeRowStyle2.setBorderLeft(BorderStyle.THICK);
            mergeRowStyle2.setBorderBottom(BorderStyle.THICK);
            mergeRowStyle2.setBorderRight(BorderStyle.THICK);
            //cellstyle 배경색 ForegroundColor
            mergeRowStyle2.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 19, (byte) 237, (byte) 102}, null));
            mergeRowStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //cell font 폰트설정
            Font headerFont2 = workbook.createFont();
            headerFont2.setFontName("나눔고딕");
            headerFont2.setBold(true);
            mergeRowStyle2.setFont(headerFont2);

            Row row2 = sheet.createRow(5);
            for (int i = 8; i < 14; i++) {
                Cell cell1 = row2.createCell(i);
                cell1.setCellStyle(mergeRowStyle2);
                if (i == 8) cell1.setCellValue("작성일자");
                else if (i == 10) cell1.setCellValue("작성자");
                else if (i == 11) cell1.setCellValue("그룹명");
                else if (i == 12) cell1.setCellValue("참여자");
            }

            // 참조데이터 get 부분
            CellStyle mergeRowStyle3 = workbook.createCellStyle();
            //가로
            mergeRowStyle3.setAlignment(HorizontalAlignment.CENTER);
            //세로
            mergeRowStyle3.setVerticalAlignment(VerticalAlignment.CENTER);

            //cellstyle 테두리 border
            mergeRowStyle3.setBorderTop(BorderStyle.THICK);
            mergeRowStyle3.setBorderLeft(BorderStyle.THICK);
            mergeRowStyle3.setBorderBottom(BorderStyle.THICK);
            mergeRowStyle3.setBorderRight(BorderStyle.THICK);
            //cell font 폰트설정
            Font headerFont3 = workbook.createFont();
            headerFont3.setFontName("나눔고딕");
            mergeRowStyle3.setFont(headerFont3);

            // 내용 중 style
            CellStyle inputStyle = workbook.createCellStyle();
            //가로
            inputStyle.setAlignment(HorizontalAlignment.CENTER);
            //세로
            inputStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            //cellstyle 테두리 border
            inputStyle.setBorderLeft(BorderStyle.THICK);
            inputStyle.setBorderRight(BorderStyle.THICK);
            //cell font 폰트설정
            inputStyle.setFont(headerFont3);

            // 내용 끝 style
            CellStyle endStyle = workbook.createCellStyle();
            //가로
            endStyle.setAlignment(HorizontalAlignment.CENTER);
            //세로
            endStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            //cellstyle 테두리 border
            endStyle.setBorderLeft(BorderStyle.THICK);
            endStyle.setBorderRight(BorderStyle.THICK);
            endStyle.setBorderBottom(BorderStyle.THICK);
            //cell font 폰트설정
            endStyle.setFont(headerFont3);

            Row row3 = sheet.createRow(6);
            for (int j = 8; j < 14; j++) {
                Cell cell4 = row3.createCell(j);
                cell4.setCellStyle(mergeRowStyle3);
                if (j == 8) cell4.setCellValue(sttReq.getDate());
                else if (j == 10) cell4.setCellValue(sttReq.getName());
                else if (j == 11) {
                    if (sttReq.getGroupName() == null) cell4.setCellValue("없음");
                    cell4.setCellValue(sttReq.getGroupName());
                } else if (j == 12) {
                    List<String> names = sttReq.getUserName();
                    cell4.setCellValue(names.get(0) + "등 " + names.size() + "명");
                }

            }
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 8, 9));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 8, 9));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 12, 13));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 12, 13));


            // 내용 참조
            Row row4 = sheet.createRow(12);
            for (int i = 1; i < 14; i++) {
                Cell cell2 = row4.createCell(i);
                cell2.setCellStyle(mergeRowStyle2);
                if (i == 1) {
                    cell2.setCellValue("시 간");
                } else if (i == 3) {
                    cell2.setCellValue("사용자");
                } else if (i == 5) {
                    cell2.setCellValue("내 용");
                }
            }
            sheet.addMergedRegion(new CellRangeAddress(12, 12, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(12, 12, 3, 4));
            sheet.addMergedRegion(new CellRangeAddress(12, 12, 5, 13));

            String checktime = " ";
            String checkname = " ";
            //내용 input
            for (int j = 13; j < 13 + sttReq.getStt().size(); j++) {
                Row row5 = sheet.createRow(j);

                for (int i = 1; i < 14; i++) {
                    Cell cell5 = row5.createCell(i);
                    cell5.setCellStyle(inputStyle);

                    if(j==12+sttReq.getStt().size())cell5.setCellStyle(endStyle);

                    if (i == 1) {
                        if(sttReq.getStt().get(j-13).getTime()==checktime){
                            cell5.setCellValue(" ");
                        }else {
                            cell5.setCellValue(sttReq.getStt().get(j-13).getTime());
                            checktime = sttReq.getStt().get(j-13).getTime();
                        }
                    } else if (i == 3) {
                        if(sttReq.getStt().get(j-13).getName()==checkname) {
                            cell5.setCellValue(" ");
                        }else {
                            cell5.setCellValue(sttReq.getStt().get(j - 13).getName());
                            checkname = sttReq.getStt().get(j - 13).getName();
                        }
                    } else if (i == 5) {
                        cell5.setCellValue(sttReq.getStt().get(j - 13).getStt());
                    }
                }
                sheet.addMergedRegion(new CellRangeAddress(j, j, 1, 2));
                sheet.addMergedRegion(new CellRangeAddress(j, j, 3, 4));
                sheet.addMergedRegion(new CellRangeAddress(j, j, 5, 13));
            }


            ;

            workbook.write(fileout);
            fileout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
