package no.minimon.snakeinspace;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GalaxySounds {

	public List<Sound> nomSounds;
	public Sound theme;
	public Sound explosion;
	public Sound blop;

	public GalaxySounds() {
		nomSounds = new ArrayList<Sound>();
		for (int i = 1; i < 10; i++) {
			nomSounds.add(Gdx.audio.newSound(Gdx.files.internal("nom" + i
					+ ".ogg")));
		}
		theme = Gdx.audio.newSound(Gdx.files.internal("theme.ogg"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		blop = Gdx.audio.newSound(Gdx.files.internal("blop.ogg"));
	}

	public long playNom(int index) {
		if (nomSounds.isEmpty())
			return -1l;
		return nomSounds.get(index).play();
	}

}
