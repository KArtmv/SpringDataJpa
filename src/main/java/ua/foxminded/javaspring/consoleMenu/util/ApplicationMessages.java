package ua.foxminded.javaspring.consoleMenu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:applicationMessages.properties")
public class ApplicationMessages {

    @Value("${addNewStudent}")
    public String inputNewStudentData;
    @Value("${inputFirsName}")
    public String inputStudentFirstName;
    @Value("${inputLastName}")
    public String inputStudentLastName;
    @Value("${addGroupToStudent}")
    public String addStudentToGroup;
    @Value("${studentAdded}")
    public String printStudentAddedSuccess;
    @Value("${studentRemoved}")
    public String printStudentRemovedSuccess;
    @Value("${enterCourseID}")
    public String inputCourseId;
    @Value("${studentAddedToCourse}")
    public String printStudentAddedToCourseSuccess;
    @Value("${addStudentToCourse}")
    public String inputStudentIdToAddToCourse;
    @Value("${removeStudentFromCourse}")
    public String inputStudentIdToRemoveFromCourse;
    @Value("${chooseEnrollmentId}")
    public String chooseEnrollmentIdToRemove;
    @Value("${studentRemovedFromCourse}")
    public String printStudentRemovedFromCourseSuccess;
    @Value("${studentHasNotCourse}")
    public String printStudentNotEnrolledInAnyCourse;
    @Value("${removeStudentByID}")
    public String inputStudentIdToRemove;
    @Value("${groupsByCount}")
    public String inputGroupSizeToFindGroups;
    @Value("${hasNotGroupWithCount}")
    public String printNoGroupWithSize;
    @Value("${studentsAtCourse}")
    public String inputCourseIdToViewEnrolledStudents;
    @Value("${nameStudyingStudent}")
    public String printStudentEnrolledInCourses;
    @Value("${courseNameAndDescription}")
    public String printCourseDetails;
    @Value("${studentsFromCourse}")
    public String printStudentsEnrolledInCourse;
    @Value("${amountStudentAtGroup}")
    public String printGroupByCountEnrollmentStudents;
    @Value("${printAllGroups}")
    public String printAllGroups;
    @Value("${verifyStudent}")
    public String confirmStudentDetails;
}
