package org;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Hello world!");
        Thread serverThread = new Thread(()->{
            try{
                server.main();
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        serverThread.start();
        Thread.sleep(5000);
        client.main();
    }
}