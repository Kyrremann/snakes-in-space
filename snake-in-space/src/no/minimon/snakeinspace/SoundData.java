package no.minimon.snakeinspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class SoundData {
	Sound s;
	String filepath;

	SoundData(String filepath) {
		this.filepath = filepath;
		this.s = loadSound(filepath);
	}

	private Sound loadSound(String string) {
		try {
			return Gdx.audio.newSound(Gdx.files.internal(string));
		} catch (GdxRuntimeException GRE) {
			return null;
		}
	}

	/**
	 * will not play a null pointer this way the game does not crash on failure
	 * to load sound file.
	 * 
	 * @param s
	 *            - sound file to load
	 * @return long id of sound
	 */
	public long play() {
		if (s != null) {
			return s.play();
		}
		System.err.println("MISSING SOUND: " + filepath);
		return -1l;
	}
	
	public void dispose() {
		s.dispose();
	}
}
