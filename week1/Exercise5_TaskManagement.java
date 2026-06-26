class Task{
    int taskId; String taskName; String status; Task next;
    Task(int taskId,String taskName,String status){
        this.taskId=taskId; this.taskName=taskName; this.status=status;
    }
}
public class Exercise5_TaskManagement{
    Task head;
}