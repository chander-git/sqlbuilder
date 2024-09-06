
package bitcomm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Message {
    private String msg;

    public Message(String str){
	this.msg=str;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String str) {
	this.msg=str;
    }

}

interface FunctionalInterfaceName
{
    public abstract void test();
    
    public boolean equals(Object object);
    
    public  int hashCode();
    
    
    
}

class Waiter implements Runnable {

    private Message msg;

    public Waiter(Message m){
	this.msg=m;

    }

    @Override
    public void run() {
	String name = Thread.currentThread().getName();
	synchronized (msg) {
	    try{
		System.out.println(name+" waiting to get notified at time:"+System.currentTimeMillis());
		msg.wait();
	    }catch(InterruptedException e){
		e.printStackTrace();
	    }
	    System.out.println(name+" waiter thread got notified at time:"+System.currentTimeMillis());
	    //process the message now
	    System.out.println(name+" processed: "+msg.getMsg());
	}
    }


}

class Notifier implements Runnable {

    private Message msg;

    public Notifier(Message msg) {
	this.msg = msg;
    }

    @Override
    public void run() {
	String name = Thread.currentThread().getName();
	System.out.println(name+" started");
	try {
	    Thread.sleep(1000);
	    synchronized (msg) {
		msg.setMsg(name+" Notifier work done");
		msg.notify();
		//		 msg.notifyAll();
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

    }

}
class Producer extends Thread
{   
    private Object mutex;

    List<String> data;
    public Producer(List<String> data,Object mutex) {
	this. data=data;
	this.mutex=mutex;
    }
    @Override
    public void run() 
    {
	while (true) 
	{
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public void add(String val)
    {
	synchronized (mutex) 
	{
	    data.add(val);
	}
	try {
	    mutex.notify();
	} catch (Exception e) {
	}
    }
}

class Consumer extends Thread
{
    private Object mutex;

    Producer data;
    public Consumer(Producer data,Object mutex)
    {
	this. data=data;
	this.mutex=mutex;
    }
    @Override
    public void run() 
    {

	while (true)
	{
	    try {
		synchronized (mutex)
		{
		    mutex.wait();
		}
		for (String string : data.data) 
		{
		    System.out.println(string);
		}
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }
}


public class App
{

    public static void main(String[] args)
    {
	Object mutext=new Object();

	String[] integers= {"1","2","3","4","5","6","7","8","9","10"};
	List<String> list=new ArrayList<String>();
	Producer printerA=new Producer(list,mutext);
	Consumer printerB=new Consumer(printerA,mutext);

	printerB.start();


	for (String val : integers)
	{	
	    printerA.add(val);
	}

	//	Message msg = new Message("process it");
	//	Waiter waiter1 = new Waiter(msg);
	//	new Thread(waiter1, "waiter1").start();
	//
	//	
	//	Waiter waiter = new Waiter(msg);
	//	new Thread(waiter,"waiter").start();
	//
	//
	//	Notifier notifier = new Notifier(msg);
	//	new Thread(notifier, "notifier").start();
	//	System.out.println("All the threads are started");
    }
}
