/*
 * Copyright 2012 Kulikov Dmitriy
 * Copyright 2017-2018 Nikita Shakarun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.microedition.media;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.protocol.DataSource;

public class Manager {
	public static final String TONE_DEVICE_LOCATOR = "device://tone";
	public static final String MIDI_DEVICE_LOCATOR = "device://midi";


	public static Player createPlayer(String locator) throws IOException {
		return null;
	}

	public static Player createPlayer(DataSource source) throws IOException, MediaException {
		return null;
	}

	public static Player createPlayer(final InputStream stream, String type) throws IOException {
		return null;
	}

	public static String[] getSupportedContentTypes(String str) {
		return null;
	}

	public static String[] getSupportedProtocols(String str) {
		return null;
	}

	public synchronized static void playTone(int frequency, int time, int volume)
			throws MediaException {
		
	}
}
