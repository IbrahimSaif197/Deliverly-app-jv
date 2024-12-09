
package com.deliverly.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class playground {
    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("src//data//users.txt"));
			String line = reader.readLine();

			while (line != null) {
				System.out.println(line);
				// read next line
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
		}
    }
    
}
