package tern.server.nodejs.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip {

	/**
	 * Extract zip file to destination folder.
	 * 
	 * @param file
	 *            zip file to extract
	 * @param destination
	 *            destinatin folder
	 */
	public static void extract(File file, File destination) throws IOException {
		ZipInputStream in = null;
		OutputStream out = null;
		try {
			// Open the ZIP file
			in = new ZipInputStream(new FileInputStream(file));

			// Get the first entry
			ZipEntry entry = null;

			while ((entry = in.getNextEntry()) != null) {
				String outFilename = entry.getName();

				// Open the output file
				File extracted = new File(destination, outFilename);
				if (entry.isDirectory()) {
					extracted.mkdirs();
				} else {
					out = new FileOutputStream(extracted);

					// Transfer bytes from the ZIP file to the output file
					byte[] buf = new byte[1024];
					int len;

					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}

					// Close the stream
					out.close();
					if (extracted.getParent().contains("/bin")) {
						extracted.setExecutable(true);
					}
				}
			}
		} finally {
			// Close the stream
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

}
