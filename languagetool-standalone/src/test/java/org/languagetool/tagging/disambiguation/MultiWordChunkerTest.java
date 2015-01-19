/* LanguageTool, a natural language style checker 
 * Copyright (C) 2011 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.tagging.disambiguation;

import junit.framework.TestCase;

import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.language.English;
import org.languagetool.language.Ukrainian;

public class MultiWordChunkerTest extends TestCase {

  public void testDisambiguate() throws Exception {
    final Disambiguator chunker = new MultiWordChunker("/pl/multiwords.txt");
    final JLanguageTool lt = new JLanguageTool(new English());
    final AnalyzedSentence analyzedSentence = lt.getAnalyzedSentence("A test... More.");
    final AnalyzedSentence disambiguated = chunker.disambiguate(analyzedSentence);
    final AnalyzedTokenReadings[] tokens = disambiguated.getTokens();
    assertTrue(tokens[4].getReadings().toString().contains("<ELLIPSIS>"));
    assertTrue(tokens[6].getReadings().toString().contains("</ELLIPSIS>"));
  }
  
  public void testDisambiguateMultiSpace() throws Exception {
      final Disambiguator chunker = new MultiWordChunker("/uk/multiwords.txt");
      final JLanguageTool lt = new JLanguageTool(new Ukrainian());
      final AnalyzedSentence analyzedSentence = lt.getAnalyzedSentence("для  годиться.");
      final AnalyzedSentence disambiguated = chunker.disambiguate(analyzedSentence);
      final AnalyzedTokenReadings[] tokens = disambiguated.getTokens();
      assertTrue(tokens[1].getReadings().toString().contains("<adv>"));
      assertTrue(tokens[4].getReadings().toString().contains("</adv>"));
    }
}
