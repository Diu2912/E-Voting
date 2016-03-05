package test.myapplication;

public class Message
{
  String sender;
  String content;

  public Message(String aSender, String aContent)
  {
    this.sender = aSender;
    this.content = aContent;
  }

  public String getSender()
  {
    return sender;
  }
  public String getContent()
  {
    return content;
  }
}
