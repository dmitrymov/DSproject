package firstConnection;

import javax.swing.*;

import net.jxta.exception.PeerGroupException;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Random;
import java.awt.Cursor;
import javax.swing.JTextArea;

public class SwingWorkerExample extends JPanel {

	private JTextArea textArea ;
	private String name;
    public SwingWorkerExample() {
    	this.name = JOptionPane.showInputDialog("Name?");
    	if(this.name.isEmpty()){
    		System.exit(-1);
    	}
        setLayout(null);
        
        textArea = new JTextArea( );
        textArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
   
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(0, 0, 369, 382);
        add(scrollPane);
             PopulationWorker populationWorker = new PopulationWorker();
        populationWorker.start();
    }

    private class LongRunningModelFillAction extends AbstractAction {
        public LongRunningModelFillAction() {
            super("Fill Model");
        }

        public void actionPerformed(ActionEvent e) {
            PopulationWorker populationWorker = new PopulationWorker();
            populationWorker.start();
            
        }
    }

    private class PopulationWorker extends SwingWorker {
        public Object construct() {
			return accessibleContext;
            
        }

        public void start() {
          int port = 9000 + new Random().nextInt(100);
          
          Peer p  = new Peer(port , name , textArea);
          try {
			p.start();
		} catch (PeerGroupException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          p.fetch_advertisements();
          super.start();
          
          
        }

        public void finished() {
           
        }
        
        
    }
    public String getName(){
    	return this.name;
    			
    }
    public static void main(String[] a){
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      SwingWorkerExample s = new SwingWorkerExample();
      frame.getContentPane().add(s);
      frame.pack();
      frame.setSize(550, 500);
      frame.setTitle(s.getName());
      frame.setVisible(true);
      
      
    } 
}


/*
 * $Id: SwingWorker.java,v 1.1.1.1 2004/06/16 01:43:39 davidson1 Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */


/**
 * This is the 3rd version of SwingWorker (also known as
 * SwingWorker 3), an abstract class that you subclass to
 * perform GUI-related work in a dedicated thread.  For
 * instructions on using this class, see:
 * 
 * http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html
 *
 * Note that the API changed slightly in the 3rd version:
 * You must now invoke start() on the SwingWorker after
 * creating it.
 */
abstract class SwingWorker {
    private Object value;  // see getValue(), setValue()
    private Thread thread;

    /** 
     * Class to maintain reference to current worker thread
     * under separate synchronization control.
     */
    private static class ThreadVar {
        private Thread thread;
        ThreadVar(Thread t) { thread = t; }
        synchronized Thread get() { return thread; }
        synchronized void clear() { thread = null; }
    }

    private ThreadVar threadVar;

    /** 
     * Get the value produced by the worker thread, or null if it 
     * hasn't been constructed yet.
     */
    protected synchronized Object getValue() { 
        return value; 
    }

    /** 
     * Set the value produced by worker thread 
     */
    private synchronized void setValue(Object x) { 
        value = x; 
    }

    /** 
     * Compute the value to be returned by the <code>get</code> method. 
     */
    public abstract Object construct();

    /**
     * Called on the event dispatching thread (not on the worker thread)
     * after the <code>construct</code> method has returned.
     */
    public void finished() {
    }

    /**
     * A new method that interrupts the worker thread.  Call this method
     * to force the worker to stop what it's doing.
     */
    public void interrupt() {
        Thread t = threadVar.get();
        if (t != null) {
            t.interrupt();
        }
        threadVar.clear();
    }

    /**
     * Return the value created by the <code>construct</code> method.  
     * Returns null if either the constructing thread or the current
     * thread was interrupted before a value was produced.
     * 
     * @return the value created by the <code>construct</code> method
     */
    public Object get() {
        while (true) {  
            Thread t = threadVar.get();
            if (t == null) {
                return getValue();
            }
            try {
                t.join();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // propagate
                return null;
            }
        }
    }


    /**
     * Start a thread that will call the <code>construct</code> method
     * and then exit.
     */
    public SwingWorker() {
        final Runnable doFinished = new Runnable() {
           public void run() { finished(); }
        };

        Runnable doConstruct = new Runnable() { 
            public void run() {
                try {
                    setValue(construct());
                }
                finally {
                    threadVar.clear();
                }

                SwingUtilities.invokeLater(doFinished);
            }
        };

        Thread t = new Thread(doConstruct);
        threadVar = new ThreadVar(t);
    }

    /**
     * Start the worker thread.
     */
    public void start() {
        Thread t = threadVar.get();
        if (t != null) {
            t.start();
        }
    }
}
