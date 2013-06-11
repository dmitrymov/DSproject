package firstTest;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.jxta.document.*;
import net.jxta.peergroup.*;
import net.jxta.exception.*;
import net.jxta.impl.peergroup.*;
import net.jxta.id.*;
import net.jxta.discovery.*;
import net.jxta.pipe.*;
import net.jxta.protocol.*;
import net.jxta.platform.*;
import net.jxta.endpoint.*;
import net.jxta.peer.*;
import net.jxta.codat.*;
public class PeerGroupIDCreator extends JFrame {
	private JTextArea displayArea;
	public static void main(String args[]) {
		PeerGroupIDCreator myapp = new PeerGroupIDCreator();
		myapp.addWindowListener (
				new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				}
				);
		myapp.run();
	}
	public PeerGroupIDCreator() {
		super("Creator");
		Container c = getContentPane();
		displayArea = new JTextArea();
		c.add (new JScrollPane(displayArea), BorderLayout.CENTER);
		setSize(300,150);
		show();
		PeerGroupID myNewPeerGroupID = (PeerGroupID)
				net.jxta.id.IDFactory.newPeerGroupID();
		displayArea.append("PeerGroupID is: " + myNewPeerGroupID +
				"\n");
		PeerID myNewPeerID = (PeerID)
				net.jxta.id.IDFactory.newPeerID(myNewPeerGroupID);
		displayArea.append("PeerID is: " + myNewPeerID + "\n");
		CodatID myCodatID = (CodatID)
				net.jxta.id.IDFactory.newCodatID(myNewPeerGroupID);
		displayArea.append("CodatID is: " + myCodatID + "\n");
		ModuleClassID myModuleClassID = (ModuleClassID)
				net.jxta.id.IDFactory.newModuleClassID();
		displayArea.append("ModuleClassID is: " + myModuleClassID
				+ "\n");
		ModuleSpecID myModuleSpecID = (ModuleSpecID)
				net.jxta.id.IDFactory.newModuleSpecID(myModuleClassID);
		displayArea.append("ModuleSpecID is: " + myModuleSpecID +
				"\n");
		PipeID myNewPipeID = (PipeID)
				net.jxta.id.IDFactory.newPipeID(myNewPeerGroupID);
		displayArea.append("PipeID is: " + myNewPipeID + "\n");
	}
	public void run() {
	}
}