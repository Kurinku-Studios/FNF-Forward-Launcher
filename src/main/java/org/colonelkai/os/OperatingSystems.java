package org.colonelkai.os;

import java.util.stream.Stream;

public enum OperatingSystems {

    WINDOWS(new WindowsSystem());

    private OperatingSystem system;

    OperatingSystems(OperatingSystem os){
        this.system = os;
    }

    public OperatingSystem getOS(){
        return this.system;
    }

    public static OperatingSystems valueOf(){
        String osTag = System.getProperty("os.name");
        return Stream.of(values()).filter(value -> osTag.toLowerCase().contains(value.system.getTypeName().toLowerCase())).findAny().orElseThrow(() -> new RuntimeException("Unknown OS"));
    }
}
