package utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Ficheros {
	
	public static final int NUM_LINEAS = 10000;
	public static final int NUM_PALABRAS_POR_LINEA = 10;
	public static final String NOMBRE_FICHERO_ALEATORIO = "/ficheroPalabrasAleatorias";
	public static final String NOMBRE_FICHERO_ZIP = "/zipPalabrasAleatorias.zip";
	public static final String CODIFICACION = "UTF-8";
	public static final int NUM_COPIAS = 5;
	
	// Texto libre que sirve para seleccionar palabras al azar con el fin de generar ficheros mas extensos
	private static String texto1 = "En un lugar de la Mancha de cuyo nombre no quiero acordarme ";
	private static String texto2 = "no ha mucho tiempo que vivía un hidalgo de los de lanza en ";
	private static String texto3 = "astillero adarga antigua rocín flaco y galgo corredor ";
	private static String texto4 = "Una olla de algo más vaca que carnero salpicón las más noches ";
	private static String texto5 = "duelos y quebrantos los sábados lentejas los viernes algún palomino ";
	private static String texto6 = "de añadidura los domingos consumían las tres partes de su hacienda ";
	private static String texto7 = "El resto della concluían sayo de velarte calzas de velludo para las ";
	private static String texto8 = "fiestas con sus pantuflos de lo mismo los días de entre semana se ";
	private static String texto9 = "honraba con su vellori de lo más fino Tenía en su casa una ama que ";
	private static String texto10 = "pasaba de los cuarenta y una sobrina que no llegaba a los veinte ";
	private static String textoCompleto = new StringBuffer(texto1).append(texto2).append(texto3)
												.append(texto4).append(texto5).append(texto6)
												.append(texto7).append(texto8).append(texto9)
												.append(texto10).toString();
	
	
    /**
     * Obtiene la lista de palabras extraidas de un texto libre que se ha elegido
     */
	private static String[] obtenerDiccionario() {
		StringTokenizer tokenizer = new StringTokenizer(textoCompleto);
		String[] palabrasDiccionario = new String[tokenizer.countTokens()];
		int i = 0;
		while(tokenizer.hasMoreTokens()) {
			palabrasDiccionario[i++] = tokenizer.nextToken();
		}
		return palabrasDiccionario;
	}
	
	
    /**
     * Obtiene una linea formada de palabras al azar procedentes del diccionario
     * @param diccionario La lista de palabras extraidas previamente del texto libre elegido
     * @param numPalabrasPorLinea Numero de palabras que componen la linea formada al azar
     */
	private static String obtenerLineaAleatoria(String[] diccionario, int numPalabrasPorLinea) {
		StringBuffer lineaAleatoria = new StringBuffer();
		Random rnd = new Random();
		for(int i = 0; i < numPalabrasPorLinea; i++) {
			int numAleat =  (int)(rnd.nextDouble() * diccionario.length);
			lineaAleatoria.append(diccionario[numAleat]).append(" ");
		}
		return lineaAleatoria.toString();
	}
	
	
    /**
     * Escribe en disco un fichero compuesto de lineas generadas al azar gracias al metodo anterior
     * @param path Ruta completa del fichero que se generara
     * @param numLineas Numero de lineas que contendra el fichero
     * @param numPalabrasPorLinea Numero de palabras que componen cada linea
     */
	private static void escribirFicheroPalabrasAleatorias(String path, int numLineas, int numPalabrasPorLinea) throws UnsupportedEncodingException, FileNotFoundException {
		String[] diccionario = obtenerDiccionario();
		PrintWriter writer = new PrintWriter(path, CODIFICACION);
		for(int i = 0; i < numLineas; i++) {
			String lineaAleatoria = obtenerLineaAleatoria(diccionario, numPalabrasPorLinea);
			writer.println(lineaAleatoria);
		}
		writer.close();
	}
	
	
    /**
     * Copia un fichero tantas veces como se desee (se añade un sufijo al nombre cada vez que se realiza una copia)
     * @param path Ruta completa del fichero que se copiara
     * @param numCopias Numero de copias
     */
	private static void copiarFichero(String path, int numCopias) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		for(int i = 2; i < (numCopias+2); i++) {
			String linea = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), CODIFICACION));
			PrintWriter writer = new PrintWriter(path+i, CODIFICACION);
			while((linea = br.readLine()) != null) {
				writer.println(linea);
			}
			br.close();
			writer.close();
		}
	}
	
	
    /**
     * Añade un fichero a un conjunto de ficheros comprimidos
     * @param fileName Ruta completa del fichero a añadir
     * @param zos Zip en el que se añadira
     */
    private static void addFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(fileName);
        int size = 0;
        byte[] buffer = new byte[1024];
 
        File file = new File(fileName);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);
 
        while ((size = fis.read(buffer, 0, buffer.length)) > 0) {
            zos.write(buffer, 0, size);
        }
 
        zos.closeEntry();
        fis.close();
    }
	
	
    /**
     * Añade un fichero a un conjunto de ficheros comprimidos (zip)
     * @param path Ruta completa del fichero a añadir
     * @param zip Ruta completa del conjunto de ficheros comprimidos
     * @param numCopias Numero de copias que se habia realizado y que se pretende añadir al zip
     */
	private static void comprimirFicherosEnUno(String path, String zip, int numCopias) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(zip);
        CheckedOutputStream checksum = new CheckedOutputStream(fos, new Adler32());
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(checksum));
        addFile(path, zos);
        for(int i = 2; i < (numCopias+2); i++) {
        	addFile(path+i, zos);
        }
        zos.close();
	}
	
	
    /**
     * Se realiza un proceso que consiste en la generacion de ficheros y su compresion con el fin de evaluar 
     * las prestaciones del servidor donde se aloja una aplicacion web
     * 
     * @param path Ruta completa del fichero a generar
     * @param zip Ruta completa del conjunto de ficheros comprimidos
     * @param numLineas Numero de lineas que contendra el fichero
     * @param numPalabrasPorLinea Numero de palabras que componen cada linea
     */
	public static void realizarProceso(String path, String zip, int numLineas, int numPalabrasPorLinea) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		escribirFicheroPalabrasAleatorias(path, numLineas, numPalabrasPorLinea);
		copiarFichero(path, NUM_COPIAS);
		comprimirFicherosEnUno(path, zip, NUM_COPIAS);
	}
}
