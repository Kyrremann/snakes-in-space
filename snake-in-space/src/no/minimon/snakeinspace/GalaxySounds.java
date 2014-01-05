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
		theme = new SoundData("theme.ogg");
		explosion = new SoundData("explosion.ogg");
		blop = new SoundData("blop.ogg");
	}

	public long playNom(int index) {
		if (nomSounds.isEmpty())
			return -1l;
		nomSounds.get(index).play();
		return 0;
	}

	public void disposeAll() {
		for (SoundData s : nomSounds)
			s.dispose();
		theme.dispose();
		explosion.dispose();
		blop.dispose();
	}
}
