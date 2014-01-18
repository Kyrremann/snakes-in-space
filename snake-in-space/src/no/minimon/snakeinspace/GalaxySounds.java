package no.minimon.snakeinspace;

import java.util.ArrayList;
import java.util.List;

public class GalaxySounds {

	public List<SoundData> nomSounds;
	public SoundData theme;
	public SoundData explosion;
	public SoundData blop;

	public GalaxySounds() {
		nomSounds = new ArrayList<SoundData>();
		for (int i = 1; i < 10; i++) {
			nomSounds.add(new SoundData("nom" + i + ".ogg"));
		}
		// theme = new SoundData("theme.ogg");
		explosion = new SoundData("explosion.ogg");
		blop = new SoundData("blop.ogg");
	}

	public int nomSoundsSize() {
		return nomSounds.size();
	}

	public long playNom(int index) {
		if (nomSounds.isEmpty())
			return -1l;
		return nomSounds.get(index).play();
	}

	public void disposeAll() {
		for (SoundData s : nomSounds)
			s.dispose();
		theme.dispose();
		explosion.dispose();
		blop.dispose();
	}

	public long playExplosion() {
		return explosion.play();
	}

	public long playBlop(float volume) {
		return blop.play(volume);
	}
}
