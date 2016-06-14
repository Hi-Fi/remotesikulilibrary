package com.github.hi_fi.remotesikulilibrary;
import org.robotframework.javalib.library.AnnotationLibrary;

public class RemoteSikuliLibrary extends AnnotationLibrary {
  public RemoteSikuliLibrary() {
    super("com/github/hi_fi/remotesikulilibrary/keywords/**");
  }
}