import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* You must enable signin from less seccure apps in 
 Gmail: https://myaccount.google.com/security 
*/
//You also need to download and include javax.mail.jar. Here is a link: https://javaee.github.io/javamail/

public class Mail extends JFrame{
	private static final long serialVersionUID = 1L;
	
	JPanel panel;
	JPanel panel2;
	JTextField recipient;
	JTextField sender;
	JTextField subject;
	JTextArea mainmessage;
	JPasswordField psw;
	JLabel label;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JButton btn;
	
	public Mail() {
		panel = new JPanel();
		panel.setLayout(null);
		
		//Add a label for the text field
		label = new JLabel("Recipient");
		label.setBounds(50, 10, 100, 40);
		panel.add(label);
		
		//Add a text field for the mail of the recipient
		recipient = new JTextField();
		recipient.setBounds(120, 15, 200, 30);
		recipient.setFocusable(true);
		panel.add(recipient);
		
		//Add a button to check if the email is valid
		btn = new JButton("Submit");
		btn.setBounds(150, 70, 100, 30);
		panel.add(btn);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String rec = recipient.getText();
				
				if(rec.isEmpty()) {//If the text field is empty
					JOptionPane.showMessageDialog(null, "You must enter a recipient");
				}
				else {
					if(!isValid(rec)) {// if the email isn't valid
						JOptionPane.showMessageDialog(null, "The email you entered is not valid");
					}
					else{
						sendMail(rec);//Recipient of the mail
					}
				}
			}
		});
		
		this.setContentPane(panel);
		this.setTitle("Sent an Email");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(400, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	public void sendMail(String recipient){
		panel2 = new JPanel();
		panel2.setLayout(null);
		
		System.out.println("Panel is changed waiting for your info & message");
		Properties prop = new Properties(); 
		/*This is for authentication. It defines if an authentication is needed for the
		email server. E.g. If its gmail, then it is mandatory to have a Username & a password.
		If you are using an email server that doesnt need authentication, then set it to false*/
		prop.put("mail.smtp.auth", "true");
		//This is for tls encryption(For gmail it has to be enabled).
		prop.put("mail.smtp.starttls.enable", "true");
		//For gmail the host is smtp.gmail.com
		prop.put("mail.smtp.host", "smtp.gmail.com");
		//For gmail the port 587
		prop.put("mail.smtp.port", "587");

		//Add a label for the text field
		label = new JLabel("Enter your Email");
		label.setBounds(20, 10, 100, 40);
		panel2.add(label);
		
		//Add a text field for the mail of the sender
		sender = new JTextField();
		sender.setBounds(170, 15, 180, 30);
		sender.setFocusable(true);
		panel2.add(sender);

		//Add a label for the text field
		label2 = new JLabel("Enter your Password");
		label2.setBounds(20, 60, 140, 40);
		panel2.add(label2);
		
		//Add a text field for the password of the sender
		psw = new JPasswordField(20);
		psw.setBounds(170, 65, 180, 30);
		panel2.add(psw);
		
		//Add a label for the text field
		label3 = new JLabel("Subject");
		label3.setBounds(70, 120, 60, 40);
		panel2.add(label3);
		
		//Add a text field for the subject of the mail
		subject = new JTextField();
		subject.setBounds(130, 125, 260, 30);
		panel2.add(subject);

		//Add a label for the text field
		label4 = new JLabel("Context");
		label4.setBounds(70, 170, 60, 40);
		panel2.add(label4);
		
		//Add a text field for the context of the mail
		mainmessage = new JTextArea();
		mainmessage.setLineWrap(true);
		mainmessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JScrollPane scroll = new JScrollPane(mainmessage);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(130, 175, 276, 230);
		panel2.add(scroll);
		
		btn = new JButton("Send");
		btn.setBounds(100, 420, 100, 30);
		panel2.add(btn);
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String myAccEmail = sender.getText(); //User mail
				String myAccPass = psw.getText(); //User password for the mail
				String mailSubject = subject.getText(); //The subject of the mail
				String mailMessage = mainmessage.getText(); //The main message of the mail
				
				if(myAccEmail.isEmpty()) {//If the mail is empty
					JOptionPane.showMessageDialog(null, "You must enter your email");
				}
				else if(myAccPass.isEmpty()) {//If the password is empty
					JOptionPane.showMessageDialog(null, "You must enter a password");
				}
				else if(myAccPass.length() < 5) {//If the length of the password is smaller than 5 characters
					JOptionPane.showMessageDialog(null, "Password must have 5 or more characters");
				}
				else if(mailMessage.isEmpty()) {//If the context of the mail is empty
					JOptionPane.showMessageDialog(null, "There must be a message!");
				}
				else {
					if(!isValid(myAccEmail)) {//If the email isn't valid
						JOptionPane.showMessageDialog(null, "The email you entered is not valid");
					}
					else{
						Session session = Session.getInstance(prop, new Authenticator() { //Make a new session
							@Override
							protected PasswordAuthentication getPasswordAuthentication() {
								// TODO Auto-generated method stub
								return new PasswordAuthentication(myAccEmail, myAccPass); //Make sure to connect to the Email with the given password
							}
						});
						
						Message message = prepareMessage(session, myAccEmail, recipient, mailSubject, mailMessage); //Prepare the message
						
						try {
							Transport.send(message); //Send the message
							System.out.println("Message sent successfully");
						} catch (MessagingException arg0) {
							arg0.printStackTrace();
							JOptionPane.showMessageDialog(null, arg0);
						}
					}
				}
				
			}
		});
		
		btn = new JButton("Close");
		btn.setBounds(300, 420, 100, 30);
		panel2.add(btn);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0); //A button to terminate the program
			}
		});
		
		this.setContentPane(panel2); //Change panel
		this.setTitle("Sent an Email");
		this.setLocation(0, 0);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	private static Message prepareMessage(Session session, String myAccEmail, String recipient, String mailSubject, String mailMessage){
		try {
			Message msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(myAccEmail));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			msg.setSubject(mailSubject);
			msg.setText(mailMessage);
			
			return msg;
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return null;
	}
	
	public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex); //Compile the regex into a pattern
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
