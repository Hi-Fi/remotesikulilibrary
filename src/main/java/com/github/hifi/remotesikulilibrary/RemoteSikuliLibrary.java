package com.github.hifi.remotesikulilibrary;
import org.robotframework.javalib.library.AnnotationLibrary;

public class RemoteSikuliLibrary extends AnnotationLibrary {
  public RemoteSikuliLibrary() {
    super("com/github/hifi/remotesikulilibrary/keywords/**");
  }
}