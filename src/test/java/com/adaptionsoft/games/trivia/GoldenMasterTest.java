package com.adaptionsoft.games.trivia;

import static org.junit.Assert.*;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class GoldenMasterTest {

	@Test
	public void verify_golden_master_output_still_matches() throws Exception {
		for (int seed = 0; seed < 10000; seed++) {
			// given
			File goldenMaster = new File("goldenmaster/goldenmaster_" + seed + ".txt");

			// when
			gameToFile(seed, "testoutput/output");

			// then
			File output = new File("testoutput/output_" + seed + ".txt");
			assertTrue("Golden master files differ for seed: " + seed, FileUtils.contentEquals(goldenMaster, output));
		}
	}

    // Run this to record the game output as golden master
    public static void main(String[] args) throws FileNotFoundException {
        create_golden_master_files();
    }

    public static void create_golden_master_files() throws FileNotFoundException {
        for (int seed = 0; seed < 10000; seed++) {
            gameToFile(seed, "goldenmaster/goldenmaster");
        }
    }


	private static void gameToFile(int seed, String fileNamePrefix) throws FileNotFoundException {
		String fileName = fileNamePrefix + "_" + seed + ".txt";
		try(PrintStream printStream = new PrintStream(fileName)){
			System.setOut(printStream);
			GameRunner.rand.setSeed(seed);
			GameRunner.main(new String[]{});
		}
	}

}
