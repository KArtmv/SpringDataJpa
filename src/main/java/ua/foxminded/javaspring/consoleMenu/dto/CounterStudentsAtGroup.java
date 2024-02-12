package ua.foxminded.javaspring.consoleMenu.dto;

public class CounterStudentsAtGroup {

    private Long studentsCount;
    private String groupName;

    public CounterStudentsAtGroup(String groupName, Long studentsCount) {
        this.studentsCount = studentsCount;
        this.groupName = groupName;
    }

    public Long getStudentsCount() {
        return studentsCount;
    }

    public String getGroupName() {
        return groupName;
    }
}
