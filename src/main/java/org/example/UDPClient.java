package org.example;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient {//описание класса клиента
    DatagramSocket s = null;//создание дейтаграммы
    int x,y,z;//переменные для переслыки
    byte[] bufX,bufY,bufZ;
    byte[] buf =new byte[512];
    private void SetData()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("UDPClient: Started");
        byte[] verCmd = { 'V', 'E', 'R', 'S' };
        System.out.println("Enter x:");
        x=scanner.nextInt();
        String string = String.valueOf(x);
        bufX = string .getBytes();
        System.out.println("Enter y:");
        y=scanner.nextInt();
        string = String.valueOf(y);
        bufY = string .getBytes();
        System.out.println("Enter z:");
        z=scanner.nextInt();
        string = String.valueOf(z);
        bufZ = string .getBytes();
        System.out.printf("x=%s; y=%s; z=%s;\n",x,y,z);
    }
    private void SendData(DatagramSocket s,DatagramPacket sendPacket)
    {
        try
        {
            s.send(sendPacket);//посылка дейтаграммы X
            // DatagramPacket sendPacketY = new DatagramPacket(bufY, bufY.length, InetAddress.getByName("127.0.0.1"), 8001);//создание дейтаграммы для отсылки данных
            sendPacket.setData(bufY);//установить массив посылаемых данных
            sendPacket.setLength(bufY.length);//установить длину посылаемых данных
            s.send(sendPacket);//посылка дейтаграммы Y
            // DatagramPacket sendPacketZ = new DatagramPacket(bufZ, bufZ.length, InetAddress.getByName("127.0.0.1"), 8001);//создание дейтаграммы для отсылки данных
            sendPacket.setData(bufZ);//установить массив посылаемых данных
            sendPacket.setLength(bufZ.length);//установить длину посылаемых данных
            s.send(sendPacket);//посылка дейтаграммы Z
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void GetData(DatagramSocket s,DatagramPacket recvPacket)
    {
        try {

            s.receive(recvPacket);//получение дейтаграммы
            String answer = new String(recvPacket.getData()).trim();//извлечение данных (версии сервера)
            System.out.println("Response to expression: " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void runClient() throws IOException {//метод клиента runClient


        try {
            s = new DatagramSocket();//привязка сокета к реальному объету
            SetData( );
            DatagramPacket sendPacket = new DatagramPacket(bufX, bufX.length, InetAddress.getByName("127.0.0.1"), 8001);//создание дейтаграммы для отсылки данных
            SendData(s,sendPacket);
            DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);//создание дейтаграммы для получения данных
            GetData(s,recvPacket);
            System.out.println("UDPClient: Ended");
        }
        finally {
            if (s != null) {
                s.close();//закрытие сокета клиента
            }  }  }
    public static void main(String[] args) {//метод main
        try {
            UDPClient client = new UDPClient();//создание объекта client
            client.runClient();//вызов метода объекта client
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
