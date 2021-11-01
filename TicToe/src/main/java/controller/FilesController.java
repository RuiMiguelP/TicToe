package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Game;

public class FilesController {
	/**
	 * Método que salva o jogo no ficheiro global dos jogos
	 */
	public static void writeObjectFile(Object serializableObj, String filepath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filepath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(serializableObj);
			objectOut.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Este método lê o ficheiro global de jogos
	 */
	public static Object readGamesFile(String filepath) {
		try {
			FileInputStream fileIn = new FileInputStream(filepath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			Object obj = objectIn.readObject();
			objectIn.close();

			return obj;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
