package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import objConverter.OBJFileLoader2;
import particles.Particle;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import skybox.SkyboxRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolBox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {

	public static void main(String[] args) {
		
		List<Terrain> terrains = new ArrayList<Terrain>();
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TextMaster.init(loader);
		MasterRenderer renderer = new MasterRenderer(loader);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		FontType font = new FontType(loader.loadFontTextureAltlas("candara"), new File("res/candara.fnt"));
		GUIText text = new GUIText("This is a test text!", 3, font, new Vector2f(0.5f, 0.5f), 0.5f, true);
		text.setColour(0, 0, 0);
		
		
		boolean itIsOnTerrain = false;
		
		//**********TERRAIN TEXTURE STUFF*********
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,
				rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		//**************************
		
		ModelData data = OBJFileLoader.loadOBJ("grassPlant");
		RawModel grassModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(),
				data.getNormals(), data.getIndices());
		ModelData data1 = OBJFileLoader.loadOBJ("bigDoor");
		RawModel leavesModel = loader.loadToVAO(data1.getVertices(), data1.getTextureCoords(),
				data1.getNormals(), data1.getIndices());
		//ModelData data2 = OBJFileLoader.loadOBJ("tree");
		//RawModel treeModel = loader.loadToVAO(data2.getVertices(), data2.getTextureCoords(),
				//data2.getNormals(), data2.getIndices());
		ModelData data4 = OBJFileLoader.loadOBJ("lamp");
		RawModel lampModel = loader.loadToVAO(data4.getVertices(), data4.getTextureCoords(),
				data4.getNormals(), data4.getIndices());
		ModelData data5 = OBJFileLoader.loadOBJ("pine");
		RawModel pineModel = loader.loadToVAO(data5.getVertices(), data5.getTextureCoords(),
				data5.getNormals(), data5.getIndices());
		
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		
		
		/*ModelData[] cubeData = new ModelData[250];
		RawModel[] cubeModel = new RawModel[250];
		TexturedModel[] cube = new TexturedModel[250];
		for(int i = 0; i < 250; i++){
			if(i < 100){
				cubeData[i] = OBJFileLoader.loadOBJ("cubeMove/block_0000" + i);
			}else{
				cubeData[i] = OBJFileLoader.loadOBJ("cubeMove/block_000" + i);
			}
			
			cubeModel[i] = loader.loadToVAO(cubeData[i].getVertices(), cubeData[i].getTextureCoords(),
					cubeData[i].getNormals(), cubeData[i].getIndices());
			cube[i] = new TexturedModel(cubeModel[i], new ModelTexture(loader.loadTexture("concrete")));
		}*/
		
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
		
		
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		//RawModel TreeModel = OBJLoader.loadObjModel("leaves", loader);
		//RawModel barkModel = OBJLoader.loadObjModel("cube4", loader);
		//ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
		//TexturedModel texturedModel = new TexturedModel(model,texture);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		TexturedModel staticModel1 = new TexturedModel(leavesModel, new ModelTexture(loader.loadTexture("concrete")));
		TexturedModel grass = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("grassPlant")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		//TexturedModel tree = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("treeTexture")));
		TexturedModel lamp = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp")));
		lamp.getTexture().setUseFakeLighting(true);
		TexturedModel pine = new TexturedModel(pineModel, new ModelTexture(loader.loadTexture("pine")));
		
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		ModelTexture treeTexture = staticModel1.getTexture();
		treeTexture.setShineDamper(20);
		treeTexture.setReflectivity(.5f);
		
		/*ModelTexture cubeTexture = grass.getTexture();
		cubeTexture.setShineDamper(20);
		cubeTexture.setReflectivity(.5f);*/
		
		//Terrain terrain = new Terrain(0,0,loader, texturePack, blendMap, "heightmap");
		terrains.add( new Terrain(0,0,loader, texturePack, blendMap, "heightmap"));
		terrains.add( new Terrain(-1,0,loader, texturePack, blendMap, "heightmap"));
		terrains.add( new Terrain(0,-1,loader, texturePack, blendMap, "heightmap"));
		terrains.add( new Terrain(-1,-1,loader, texturePack, blendMap, "heightmap"));
		terrains.add( new Terrain(1,0,loader, texturePack, blendMap, "heightmap"));
		
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		
		normalMapEntities.add(new Entity(barrelModel, new Vector3f(75, 10, -75), 0, 0, 0, 1f));
		
		
		//Entity entity = new Entity(staticModel, new Vector3f(0,0,-50),0,0,0,1);
		Entity entity1 = new Entity(staticModel1, new Vector3f(0,0,-100),0,90,0,3);
		entities.add(entity1);
		//Entity cubeEntity = new Entity(grass, new Vector3f(0,0,-30),0,0,0,1);
		
		List<Light> lights = new ArrayList<Light>();
		
		Vector3f sunLight;
		Light sun = new Light(new Vector3f(0,1000,-1000),new Vector3f(1,1,1));
		lights.add(sun);
		lights.add(new Light(new Vector3f(183, 10, -293), new Vector3f(2,0,0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0,2,2), new Vector3f(1, 0.01f, 0.002f)));
		//lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2,2,0), new Vector3f(1, 0.01f, 0.002f)));
		
		entities.add(new Entity(lamp, new Vector3f(183, -4.7f, -293), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));
		
		
		//MasterRenderer renderer = new MasterRenderer(loader);
		
		/*ModelData data3 = OBJFileLoader.loadOBJ("human");
 		RawModel bunnyModel = loader.loadToVAO(data3.getVertices(), data3.getTextureCoords(),
				data3.getNormals(), data3.getIndices());
 		ModelData data31 = OBJFileLoader.loadOBJ("human1");
 		RawModel bunnyModel1 = loader.loadToVAO(data31.getVertices(), data31.getTextureCoords(),
				data31.getNormals(), data31.getIndices());
 		ModelData data32 = OBJFileLoader.loadOBJ("human2");
 		RawModel bunnyModel2 = loader.loadToVAO(data32.getVertices(), data32.getTextureCoords(),
				data32.getNormals(), data32.getIndices());
 		ModelData data33 = OBJFileLoader.loadOBJ("human3");
 		RawModel bunnyModel3 = loader.loadToVAO(data33.getVertices(), data33.getTextureCoords(),
				data33.getNormals(), data33.getIndices());
 		ModelData data34 = OBJFileLoader.loadOBJ("human4");
 		RawModel bunnyModel4 = loader.loadToVAO(data34.getVertices(), data34.getTextureCoords(),
				data34.getNormals(), data34.getIndices());
 		ModelData data35 = OBJFileLoader.loadOBJ("human5");
 		RawModel bunnyModel5 = loader.loadToVAO(data35.getVertices(), data35.getTextureCoords(),
				data35.getNormals(), data35.getIndices());
 		List<String> filesNamesList = new ArrayList<String>();

 		filesNamesList.add("suit01lres_diffuse");
 		filesNamesList.add("young_lightskinned_male_diffuse");
 		filesNamesList.add("classicshoes_texture_diffuse");
 		filesNamesList.add("blue_eye");
 		filesNamesList.add("eyebrow011");
 		filesNamesList.add("male02_diffuse_black");
 		filesNamesList.add("blue_eye");
 		filesNamesList.add("blue_eye");
 		filesNamesList.add("blue_eye");
 		filesNamesList.add("blue_eye");
 		filesNamesList.add("blue_eye");
 		filesNamesList.add("blue_eye");
 		
 		List<TexturedModel> stanfordBunny = new ArrayList<TexturedModel>();
 		
 		/*for(String files : filesNamesList){
 			stanfordBunny.add(new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("/textures/" + files))));
 		}
 		stanfordBunny.add(new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("/textures/" + filesNamesList.get(0)))));
 		stanfordBunny.add(new TexturedModel(bunnyModel1, new ModelTexture(loader.loadTexture("/textures/" + filesNamesList.get(1)))));
 		stanfordBunny.add(new TexturedModel(bunnyModel2, new ModelTexture(loader.loadTexture("/textures/" + filesNamesList.get(2)))));
 		stanfordBunny.add(new TexturedModel(bunnyModel3, new ModelTexture(loader.loadTexture("/textures/" + filesNamesList.get(3)))));
 		stanfordBunny.add(new TexturedModel(bunnyModel4, new ModelTexture(loader.loadTexture("/textures/" + filesNamesList.get(4)))));
 		stanfordBunny.add(new TexturedModel(bunnyModel5, new ModelTexture(loader.loadTexture("/textures/" + filesNamesList.get(5)))));
		List<Player> player = new ArrayList<Player>();
		
		int playerX = 0;
		int playerZ = 0;
		
		for(TexturedModel bunnyModel12 : stanfordBunny){
			player.add(new Player(bunnyModel12, new Vector3f(playerX,0,playerZ), 0, 180, 0, .5f));
		}
		
		Camera camera = new Camera(player.get(0));
		
		for(Player p : player){
			entities.add(p);
		}*/
		
		ModelData data3 = OBJFileLoader.loadOBJ("humanAll2");
		RawModel bunnyModel = loader.loadToVAO(data3.getVertices(), data3.getTextureCoords(),
				data3.getNormals(), data3.getIndices());
 		
		TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("humanTexture2")));
		
		Player player = new Player(stanfordBunny, new Vector3f(0,0,0), 0, 180, 0, .5f);
		
		entities.add(player);
		
		Camera camera = new Camera(player);
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		/*GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		GuiTexture gui1 = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.2f, 0.5f), new Vector2f(0.25f, 0.25f));
		guis.add(gui1);*/
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrains);
		
		Entity lampEntity = new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1);
		entities.add(lampEntity);
		
		Light light = new Light(new Vector3f(293, 7, -305), new Vector3f(0,2,2), new Vector3f(1, 0.01f, 0.002f));
		lights.add(light);
		
		//***********Water Render Set-up************************
		
		WaterFrameBuffers fbos = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), fbos, 0.54f, 0.62f, 0.69f);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		for(int rows=0;rows<7;rows++){
			for(int cols=0;cols<7;cols++){
				waters.add(new WaterTile(60 + (120*rows), -60 - (120*cols), 0));
			}
		}
		
		waters.remove(0);
		
		WaterTile water = new WaterTile(60,-60,0);
		
		waters.add(water);
		/*
		waters.add(new WaterTile(32, -96, 0));
		waters.add(new WaterTile(32, -160, 0));
		waters.add(new WaterTile(32, -224, 0));
		waters.add(new WaterTile(32, -288, 0));
		waters.add(new WaterTile(32, -352, 0));
		waters.add(new WaterTile(32, -416, 0));
		waters.add(new WaterTile(32, -480, 0));
		waters.add(new WaterTile(32, -544, 0));
		waters.add(new WaterTile(32, -608, 0));
		waters.add(new WaterTile(32, -672, 0));
		waters.add(new WaterTile(32, -736, 0));
		waters.add(new WaterTile(32, -800, 0));*/
		
		
		
		//waters.add(water);
		//waters.add(water2);
		
		
		for(int i = 0; i < 500; i++){
			float x = (float) ((Math.random() * 1600) - 800);
			float z = (float) ((Math.random() * 1600) - 800);
			float y = 0;
			for(Terrain each:terrains){
				if(each.checkTerrain((int)x, (int)z) == true){
					y = (float)each.getHeightOfTerrain(x, z);
				}
			}
			
			if(y <= water.getHeight()){
				continue;
			}
			
			//float y = terrain.getHeightOfTerrain(x, z);
			entities.add( new Entity(grass, new Vector3f(x, y, z),0,0,0,3));
		}
		for(int i = 0; i < 100; i++){
			float x = (float) ((Math.random() * 1600) - 800);
			float z = (float) ((Math.random() * 1600) - 800);
			float y = 0;
			for(Terrain each:terrains){
				if(each.checkTerrain((int)x, (int)z) == true){
					y = (float)each.getHeightOfTerrain(x, z);
				}
			}
			
			if(y <= water.getHeight()){
				continue;
			}
			//float y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(pine, new Vector3f(x, y-1, z),
					0,(float)(Math.random()*360),0,(float)(Math.random()*1)+1, 
					(float)(Math.random()*1)+1));
		}
		
		for(int i = 0; i < 200; i++){
			float x = (float) ((Math.random() * 1600) - 800);
			float z = (float) ((Math.random() * 1600) - 800);
			float y = 0;
			for(Terrain each:terrains){
				if(each.checkTerrain((int)x, (int)z) == true){
					y = (float)each.getHeightOfTerrain(x, z);
				}
			}
			
			if(y <= water.getHeight()){
				continue;
			}
			entities.add(new Entity(fern,(int)(Math.random()*4), new Vector3f(x, y, z),
					0,(float)(Math.random()*360), 0, 0.9f));
		}
		
		ParticleTexture particletexture = new ParticleTexture(loader.loadTexture("particleAtlas"), 4);
		ParticleTexture particletexture2 = new ParticleTexture(loader.loadTexture("fire"), 8);
		
		ParticleSystem system = new ParticleSystem(particletexture, 100, 10, 0.3f, 3, 3);
		system.randomizeRotation();
		system.setDirection(new Vector3f(0, 1, 0), 0.4f);
		system.setLifeError(0.1f);
		system.setSpeedError(0.6f);
		system.setScaleError(0.8f);
		
		ParticleSystem system2 = new ParticleSystem(particletexture2, 50, 0, 0, 2, 3);
		system2.randomizeRotation();
		system2.setDirection(new Vector3f(0, 1, 0), 0.1f);
		system2.setLifeError(0.1f);
		system2.setSpeedError(0.4f);
		system2.setScaleError(0.8f);

		
		//**********Game Loop***********************************
		
		/*int counter = 0;
		int i = 0;
		Entity block = new Entity(cube[i], new Vector3f(100, 10, 100),
				0, 0, 0, 0.9f);
		entities.add(block);
		float cubeZ = 100;*/
		
		while(!Display.isCloseRequested()){
			
			camera.move();
			
			picker.update();
			
			//system.generateParticles(player.getPosition());
			system.generateParticles(new Vector3f(100,0,10));
			system.generateParticles(new Vector3f(player.getPosition().x,player.getPosition().y+9,player.getPosition().z));
			//system2.generateParticles(new Vector3f(0,0,0));
			
			
			ParticleMaster.update(camera);
			
			lights.remove(sun);
			sunLight = renderer.sunLight();
			sun = new Light(new Vector3f(0,1000,-1000),sunLight);
			lights.add(sun);
			/*Vector3f terrainPoint = picker.getCurrentTerrainPoint();
			if(terrainPoint!=null){
				lampEntity.setPosition(terrainPoint);
				light.setPosition(new Vector3f(terrainPoint.x, terrainPoint.y+15, terrainPoint.z));
			}*/
			/*counter++;
			if(counter % 1 == 0){
				
				i++;
				if( i == 249){
					i = 0;
				}
				entities.remove(block);
				block = new Entity(cube[i], new Vector3f(100, 10, cubeZ),
						0, 0, 0, 0.9f);
				entities.add(block);
				if(i <= 125){
					cubeZ--;
				}else{
					cubeZ++;
				}
				
			}*/
			
			
			//************* Check terrain in which player is on ********************
			
			itIsOnTerrain = false;
			for(Terrain each:terrains)
			{
				if((each.checkTerrain((int)player.getPosition().x, (int)player.getPosition().z)) == true){
					
					player.move(each);
					
					
					itIsOnTerrain = true;
				}else{
					
				}
				renderer.processTerrain(each);
			}
			
			if(!itIsOnTerrain){	
				player.move();
			}
			
			//***********************************************************************
			
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			
			//render reflection texture
			fbos.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			camera.invertRoll();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()+1f));
			camera.getPosition().y += distance;
			camera.invertPitch();
			camera.invertRoll();
			
			//render refraction texture
			fbos.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()+1f));
			fbos.unbindCurrentFrameBuffer();
		
			
			
			
			//render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 10000));
			waterRenderer.render(waters, camera, sun);
			
			ParticleMaster.renderParticles(camera);
			
			guiRenderer.render(guis);
			TextMaster.render();
			
			DisplayManager.updateDisplay();	
		}
		
		//**********Clean Up Below****************
		
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		fbos.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.clenUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
