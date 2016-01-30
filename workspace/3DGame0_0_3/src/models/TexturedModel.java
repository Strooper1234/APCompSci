package models;

import textures.ModelTexture;

public class TexturedModel {
	
	private RawModel rawModel;
	private ModelTexture texture;
	
	public TexturedModel(RawModel mode1, ModelTexture texture){
		this.rawModel = mode1;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	

}
