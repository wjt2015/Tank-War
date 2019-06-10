package game_content;


import javafx.scene.media.AudioClip;
import map_tools.Level;
import resources_classes.GameSound;
import resources_classes.ScaledImage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class GameWindow extends JFrame {

	private final int windowWidth = 800;
	private final int windowHeight = 660;
	private String fontName = "cootuecursessquare16x16";
	private AudioClip music;

	public GameWindow() {

		initUI();
		playMusic();
	}

	private void playMusic(){
		music = GameSound.getMenuMusicInstance();
		music.play();
	}

	/**
	 * This method initialises the UI of the app.
	 */
	private void initUI() {

		setLayout(null);
		setSize(windowWidth,windowHeight);
		createFont();

		MenuPanel menuPanel = new MenuPanel();
		add(menuPanel);


//		menuPanel.setVisible(false);

		GameField field = new GameField(Level.ONE);

		setResizable(false);
//		add(field);
//		pack();
//		getContentPane().setBackground(Color.ORANGE);

		setTitle("Tank War");
		setWindowIcon();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void createFont(){
		try{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/mainFont.ttf")));
		} catch (Exception e){
			System.out.println("font adding failed");
		}

	}

	private void setWindowIcon(){
		Image iconImage =
				Toolkit.getDefaultToolkit().createImage("resources/sprites/window/icon.png");
		setIconImage(iconImage);
	}

	class MenuPanel extends JPanel{

		public MenuPanel(){
			setBounds(0,0,windowWidth,windowHeight);
			setLayout(null);
			addText();
			addPlayButton();
			addBackground();

		}

		private void addBackground(){
			Image backgroundImage =
					Toolkit.getDefaultToolkit().createImage("resources/sprites/menu/background2.gif");
			Image scaledImage = backgroundImage.getScaledInstance(windowWidth,windowHeight-30,Image.SCALE_DEFAULT);
			JLabel label = new JLabel(new ImageIcon(scaledImage));
			label.setBounds(0, 0, windowWidth, windowHeight - 30);
			add(label);

		}


		private void addText(){
			JLabel gameName = new JLabel("<html><div style='text-align: center;'>Tank<br>War</div></html>");
			gameName.setFont(new Font(fontName,1,100));
			gameName.setForeground(new Color(172,17,21));

			gameName.setBounds(200,-100,600,500);
			add(gameName);
		}

		private void addPlayButton(){
			JButton playButton = new JButton("Play");
			playButton.setFont(new Font(fontName,1,60));
			playButton.setForeground(Color.BLACK);
			playButton.setBackground(new Color(172,17,21));
			playButton.setBounds(250,500,300,80);
			playButton.setBorderPainted(false);
			playButton.setFocusPainted(false);
			playButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GameWindow.this.remove(MenuPanel.this);
					music.stop();
					GameFieldPanel gameFieldPanel = new GameFieldPanel(Level.THREE);
					GameWindow.this.add(gameFieldPanel);
					GameWindow.this.repaint();
					gameFieldPanel.requestFocusField();
				}
			});
			add(playButton);
		}


	}

	class GameFieldPanel extends JPanel{

		private GameField gameField;
		private Level level;
		private boolean mutedBoolean;
		private Image mutedImage = ScaledImage.create("resources\\sprites\\menu\\buttons_icon\\mute_button.png",50,50);
		private Image unmutedImage = ScaledImage.create("resources\\sprites\\menu\\buttons_icon\\unmute_button.png",50,50);

		//Level.values()[Level.Two.ordinal()+1]
		public GameFieldPanel(Level level){
			setBounds(0,0,windowWidth,windowHeight);
			setLayout(null);
			this.level = level;
			addGameField();
			addMuteButton();
		}

		private void addGameField(){
			gameField = new GameField(level);
			gameField.setBounds(0,0,624,624);
			add(gameField);
			gameField.musicPlay();
		}

		private void addMuteButton(){

			JButton muteButton = new JButton(new ImageIcon(unmutedImage));
			muteButton.setBounds(700,500,50,50);
			muteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (mutedBoolean){
						muteButton.setIcon(new ImageIcon(unmutedImage));
						gameField.musicPlay();
						mutedBoolean = false;
						requestFocusField();
					} else {
						muteButton.setIcon(new ImageIcon(mutedImage));
						gameField.musicStop();
						mutedBoolean=true;
						requestFocusField();
					}
				}
			});
			add(muteButton);
		}

		public void requestFocusField(){
			gameField.requestFocus();
		}



	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			JFrame ex = new GameWindow();
			ex.setVisible(true);
		});
	}
}