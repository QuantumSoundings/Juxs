package mods.juxs.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Ogg {
	int audio_channels;
	int audio_sample_rate;
	long dataLength;
	long headerStart;
	long sampleNum;
	int vorbis_version;

	public Ogg(File file) throws Exception {
		this(file.getPath());
	}

	public Ogg(String file) throws Exception {
		dataLength = new File(file).length();
		FileInputStream inStream = new FileInputStream(file);

		int pos = 0;
		while (true) {
			int packet_type = 0;
			char[] capture_pattern1 = { 'v','o','r','b','i','s' };
			for (int i = 0; i < capture_pattern1.length; i++) {
				int b = inStream.read();
				if (b == -1)
					throw new Exception(
						"no Vorbis identification header");
				pos++;
				if (b != capture_pattern1[i]) {
					packet_type = b;
					i = -1;
				}
			}

			if (packet_type == 1)
				break;
		}

		vorbis_version = read32Bits(inStream);
		if (vorbis_version > 0)
			throw new Exception("unknown vorbis_version "
				+ vorbis_version);
		audio_channels = inStream.read();
		audio_sample_rate = read32Bits(inStream);
		pos += 4 + 1 + 4;

		headerStart = dataLength - 16 * 1024;
		inStream.skip(headerStart - pos);
		int count = 0;
		while (true) {
			char[] capture_pattern = { 'O', 'g', 'g', 'S', 0 };
			int i;
			for (i = 0; i < capture_pattern.length; i++) {
				int b = inStream.read();
				if (b == -1)
					break;
				if (b != capture_pattern[i]) {
					headerStart += i + 1;
					i = -1;
				}
			}
			if (i < capture_pattern.length)
				break;

			count++;
//			System.out.println(count
//				+ " OggS at " + firstHeaderStart);

			int header_type_flag = inStream.read();
			if (header_type_flag == -1)
				break;

			long absolute_granule_position = 0;
			for (i = 0; i < 8; i++) {
				long b = inStream.read();
				if (b == -1)
					break;

				absolute_granule_position |= b << (8 * i);
			}
			if (i < 8)
				break;

			if ((header_type_flag & 0x06) != 0) {
/*				System.out.print("OggS " + count
					+ " at " + headerStart);
				if ((header_type_flag & 0x01) != 0)
					System.out.print(": continued");
				if ((header_type_flag & 0x02) != 0)
					System.out.print(": first");
				if ((header_type_flag & 0x04) != 0)
					System.out.print(": last");
				System.out.println(" " + absolute_granule_position);
*/				sampleNum = absolute_granule_position;
			}
		}
	}

	long getSeconds() {
		System.out.println(audio_sample_rate+" "+sampleNum);
		if (audio_sample_rate > 0)
			return sampleNum / audio_sample_rate;
		else
			return sampleNum/44100;
	}
	public int read32Bits(InputStream inStream) throws Exception {
		int n = 0;
		for (int i = 0; i < 4; i++) {
			int b = inStream.read();
			if (b == -1)
				throw new Exception("Unexpected end of input stream");
			n |= b << (8 * i);
		}
		return n;
	}

	public void showInfo() {
		System.out.println("audio_channels = " + audio_channels);
		System.out.println("audio_sample_rate = " + audio_sample_rate);
		System.out.println("dataLength = " + dataLength);
		System.out.println("seconds = " + getSeconds());
		System.out.println("headerStart = " + headerStart);
		System.out.println("vorbis_version = " + vorbis_version);
	}
}