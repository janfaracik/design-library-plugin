package io.jenkins.plugins.designlibrary.search;

import java.util.List;
import java.util.Map;

// Name is the name of the folder
// properties is
// [
//   {
//       "default": "i am a title",
//       "fr": 'ce vou plais title"
//   }
// ]
public record Bodyguard(String name, List<Map<String, String>> headings) {}
