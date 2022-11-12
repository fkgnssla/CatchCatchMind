import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class CreateRoomFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CreateRoomFrame frame = new CreateRoomFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public CreateRoomFrame(LobbyPanel lp) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 351);
		setLocationRelativeTo(null);//창이 가운데 나오게
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel createRoomLabel = new JLabel("방 생성하기");
		createRoomLabel.setFont(new Font("맑은 고딕", Font.BOLD, 35));
		createRoomLabel.setBounds(12, 22, 207, 74);
		contentPane.add(createRoomLabel);
		
		JLabel lblNewLabel = new JLabel("방 제목");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel.setBounds(12, 130, 76, 24);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		textField.setBounds(100, 130, 127, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("모드");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel_1.setBounds(12, 188, 50, 15);
		contentPane.add(lblNewLabel_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("3인용");
		chckbxNewCheckBox.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		chckbxNewCheckBox.setBounds(100, 184, 107, 23);
		contentPane.add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("4인용");
		chckbxNewCheckBox_1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		chckbxNewCheckBox_1.setBounds(223, 184, 107, 23);
		contentPane.add(chckbxNewCheckBox_1);
		
		JLabel lblNewLabel_2 = new JLabel("생성하기");
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(37, 249, 107, 33);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//서버로 Room 데이터 보내고 Frame 종료
			}
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_2.setFont(new Font("맑은 고딕",Font.BOLD,26));
				lblNewLabel_2.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				lblNewLabel_2.setFont(new Font("맑은 고딕",Font.BOLD,24));
				lblNewLabel_2.setForeground(Color.BLACK);
			}
		});
		JLabel lblNewLabel_2_1 = new JLabel("돌아가기");
		lblNewLabel_2_1.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		lblNewLabel_2_1.setBounds(186, 249, 107, 33);
		contentPane.add(lblNewLabel_2_1);
		lblNewLabel_2_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//Frame 종료
			}
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_2_1.setFont(new Font("맑은 고딕",Font.BOLD,26));
				lblNewLabel_2_1.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				lblNewLabel_2_1.setFont(new Font("맑은 고딕",Font.BOLD,24));
				lblNewLabel_2_1.setForeground(Color.BLACK);
			}
		});
	}
}
