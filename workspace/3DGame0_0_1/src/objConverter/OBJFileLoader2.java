package objConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class OBJFileLoader2 {
	
	private static final String RES_LOC = "res/";


	public static ModelData loadOBJ(String objFileName, int lineOn1) {
		int lineOn = lineOn1;
		FileReader isr = null;
		List<File> objFile = new ArrayList<File>();
		objFile.add(new File(RES_LOC + objFileName + ".obj"));
		try {
			isr = new FileReader(objFile.get(0));
		} catch (FileNotFoundException e) {
			System.err.println("File not found in res; don't use any extention");
		}
		BufferedReader reader = new BufferedReader(isr);
		String line;
		/*List<Vertex> vertices = new ArrayList<Vertex>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();*/
		
		List<List<Vertex>> verticesList = new ArrayList<List<Vertex>>();
		List<List<Vector2f>> texturesList = new ArrayList<List<Vector2f>>();
		List<List<Vector3f>> normalsList = new ArrayList<List<Vector3f>>();
		List<List<Integer>> indicesList = new ArrayList<List<Integer>>();
		
		verticesList.add(new ArrayList<Vertex>());
		texturesList.add(new ArrayList<Vector2f>());
		normalsList.add(new ArrayList<Vector3f>());
		indicesList.add(new ArrayList<Integer>());
		int objects = 0;
		int lines = 0;
		try {
			while(true){
				List<Vertex> vertices = verticesList.get(verticesList.size()-1);
				List<Vector2f> textures = texturesList.get(texturesList.size()-1);
				List<Vector3f> normals = normalsList.get(normalsList.size()-1);
				List<Integer> indices = indicesList.get(indicesList.size()-1);
				
				System.out.println("different Lists");
				
				while (true) {
					line = reader.readLine();
					if (line.startsWith("v ")) {
						String[] currentLine = line.split(" ");
						Vector3f vertex = new Vector3f((float) Float.valueOf(currentLine[1]),
								(float) Float.valueOf(currentLine[2]),
								(float) Float.valueOf(currentLine[3]));
						Vertex newVertex = new Vertex(vertices.size(), vertex);
						vertices.add(newVertex);

					} else if (line.startsWith("vt ")) {
						String[] currentLine = line.split(" ");
						Vector2f texture = new Vector2f((float) Float.valueOf(currentLine[1]),
								(float) Float.valueOf(currentLine[2]));
						textures.add(texture);
					} else if (line.startsWith("vn ")) {
						String[] currentLine = line.split(" ");
						Vector3f normal = new Vector3f((float) Float.valueOf(currentLine[1]),
								(float) Float.valueOf(currentLine[2]),
								(float) Float.valueOf(currentLine[3]));
						normals.add(normal);
					} else if(line.startsWith("o ")){
						
						//line = reader.readLine();
						if(objects >= 1){
							verticesList.add(new ArrayList<Vertex>());
							texturesList.add(new ArrayList<Vector2f>());
							normalsList.add(new ArrayList<Vector3f>());
							indicesList.add(new ArrayList<Integer>());
							
							
							System.out.println("o");
							continue;
						}
						System.out.println(lines);
						
						objects++;
						
						
						
						
						//lines = line.length();
						//return loadOBJ(objFileName, lineOn);
					}while(line != null && line.startsWith("f ")) {
						String[] currentLine = line.split(" ");
						String[] vertex1 = currentLine[1].split("/");//
						String[] vertex2 = currentLine[2].split("/");// maybe problems with vertex 1, 2 and 3
						String[] vertex3 = currentLine[3].split("/");//
						processVertex(vertex1, vertices, indices);
						processVertex(vertex2, vertices, indices);
						processVertex(vertex3, vertices, indices);
						line = reader.readLine();
						lines++;
						
					}if(line == null){
						break;
					}
					lines++;
				}
				
				
				
				
				
				
				
				reader.close();
			}
			
		} catch (IOException e) {
			System.err.println("Error reading the file");
		}
		
		ModelData data = null;
		List<float[]> verticesArray1 = new ArrayList<float[]>();
		List<float[]> texturesArray1 = new ArrayList<float[]>();
		List<float[]> normalsArray1 = new ArrayList<float[]>();
		
		
		float[] texturesArray = null;
		float[] normalsArray = null;
		List<Float> furthest = new ArrayList<Float>();
		int[] indicesArray;
		float[] verticesArray = null;
		
		verticesArray1.add(verticesArray);
		texturesArray1.add(texturesArray);
		normalsArray1.add(normalsArray);
		
		for(List<Vertex> vertices : verticesList){
			removeUnusedVertices(vertices);
			verticesArray = verticesArray1.get(0);
			verticesArray = new float[vertices.size() * 3];
			
			for(List<Vector2f> textures : texturesList){
				texturesArray = texturesArray1.get(0);
				texturesArray = new float[vertices.size() * 2];
				
				for(List<Vector3f> normals : normalsList){
					normalsArray = normalsArray1.get(0);
					normalsArray = new float[vertices.size() * 3];
					
					for(List<Integer> indices : indicesList){
						
						
						furthest.add(convertDataToArrays(vertices, textures, normals, verticesArray,
								texturesArray, normalsArray));
						indicesArray = convertIndicesListToArray(indices);
						data = new ModelData(verticesArray, texturesArray, normalsArray, indicesArray,
								furthest.get(0));
						System.out.println(lines);
						
						furthest.clear();
					}
					normalsArray1.clear();
				}
				texturesArray1.clear();
			}
			verticesArray1.clear();
		}
		return data;
		
	}

	private static void processVertex(String[] vertex, List<Vertex> vertices, List<Integer> indices) {
		int index = Integer.parseInt(vertex[0]) - 1;
		Vertex currentVertex = vertices.get(index);
		int textureIndex = Integer.parseInt(vertex[1]) - 1;
		int normalIndex = Integer.parseInt(vertex[2]) - 1;
		if (!currentVertex.isSet()) {
			currentVertex.setTextureIndex(textureIndex);
			currentVertex.setNormalIndex(normalIndex);
			indices.add(index);
		} else {
			dealWithAlreadyProcessedVertex(currentVertex, textureIndex, normalIndex, indices,
					vertices);
		}
	}

	private static int[] convertIndicesListToArray(List<Integer> indices) {
		int[] indicesArray = new int[indices.size()];
		for (int i = 0; i < indicesArray.length; i++) {
			indicesArray[i] = indices.get(i);
		}
		return indicesArray;
	}

	private static float convertDataToArrays(List<Vertex> vertices, List<Vector2f> textures,
			List<Vector3f> normals, float[] verticesArray, float[] texturesArray,
			float[] normalsArray) {
		float furthestPoint = 0;
		for (int i = 0; i < vertices.size(); i++) {
			Vertex currentVertex = vertices.get(i);
			if (currentVertex.getLength() > furthestPoint) {
				furthestPoint = currentVertex.getLength();
			}
			Vector3f position = currentVertex.getPosition();
			Vector2f textureCoord = textures.get(currentVertex.getTextureIndex());
			Vector3f normalVector = normals.get(currentVertex.getNormalIndex());
			verticesArray[i * 3] = position.x;
			verticesArray[i * 3 + 1] = position.y;
			verticesArray[i * 3 + 2] = position.z;
			texturesArray[i * 2] = textureCoord.x;
			texturesArray[i * 2 + 1] = 1 - textureCoord.y;
			normalsArray[i * 3] = normalVector.x;
			normalsArray[i * 3 + 1] = normalVector.y;
			normalsArray[i * 3 + 2] = normalVector.z;
		}
		return furthestPoint;
	}

	private static void dealWithAlreadyProcessedVertex(Vertex previousVertex, int newTextureIndex,
			int newNormalIndex, List<Integer> indices, List<Vertex> vertices) {
		if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
			indices.add(previousVertex.getIndex());
		} else {
			Vertex anotherVertex = previousVertex.getDuplicateVertex();
			if (anotherVertex != null) {
				dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex, newNormalIndex,
						indices, vertices);
			} else {
				Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition());
				duplicateVertex.setTextureIndex(newTextureIndex);
				duplicateVertex.setNormalIndex(newNormalIndex);
				previousVertex.setDuplicateVertex(duplicateVertex);
				vertices.add(duplicateVertex);
				indices.add(duplicateVertex.getIndex());
			}

		}
	}
	
	private static void removeUnusedVertices(List<Vertex> vertices){
		for(Vertex vertex:vertices){
			if(!vertex.isSet()){
				vertex.setTextureIndex(0);
				vertex.setNormalIndex(0);
			}
		}
	}

}