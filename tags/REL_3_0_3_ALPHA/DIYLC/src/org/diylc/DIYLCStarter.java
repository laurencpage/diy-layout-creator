package org.diylc;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.diylc.gui.MainFrame;
import org.diylc.presenter.Presenter;

import com.diyfever.gui.miscutils.PropertyInjector;

/**
 * Main class that runs DIYLC.
 * 
 * @author Branislav Stojkovic
 * 
 * @see Presenter
 * @see MainFrame
 */
public class DIYLCStarter {

	private static final Logger LOG = Logger.getLogger(Presenter.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		URL url = DIYLCStarter.class.getResource("log4j.properties");
		Properties properties = new Properties();
		try {
			properties.load(url.openStream());
			PropertyConfigurator.configure(properties);
		} catch (Exception e) {
			LOG.error("Could not initialize log4j configuration", e);
		}
		
		LOG.info("Starting DIYLC with working directory " + System.getProperty("user.dir"));

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			LOG.error("Could not set Look&Feel", e);
		}

		MainFrame mainFrame = new MainFrame();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		
		url = DIYLCStarter.class.getResource("/config.properties");
		properties = new Properties();
		try {
			LOG.info("Injecting default properties.");
			properties.load(new FileInputStream("config.properties"));
			PropertyInjector.injectProperties(properties);
		} catch (Exception e) {
			LOG.error("Could not read config.properties file", e);
		}

	}
}
