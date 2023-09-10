package stubs.util;

import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetFile {
  /**
   * Читает файл из папки resources
   *
   * @param fileName путь до файла в папке resources
   * @return содержание файл
   */
  @SneakyThrows
  public static String readFile(String fileName) {
    URL url = GetFile.class.getClassLoader().getResource(fileName);
    Path path = Paths.get(Objects.requireNonNull(url).toURI());
    try (Stream<String> stringStream = Files.lines(path)) {
      return stringStream.collect(Collectors.joining(System.lineSeparator()));
    }
  }
}
