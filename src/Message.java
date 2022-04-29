public class Message {
  private String type;
  private String body;
  private String author;
  
  public Message(String type, String author, String body) {
    this.type = type;
    this.author = author;
    this.body = body;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
  
  @Override
  public String toString() {
    return this.getType() + " " + this.getAuthor() + " " + this.getBody();
  }
}

