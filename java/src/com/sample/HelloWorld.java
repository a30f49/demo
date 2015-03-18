package com.sample;

class HelloWorld {
   private String message;
   
   public String getMessage(){
      return message;
   }


   public void setMessage(String message){
      this.message = message;
   }


   public static void main(String[] args){
       HelloWorld hello = new HelloWorld();
       hello.setMessage("Hello World!");

       System.out.println(hello.getMessage());
   }
}
