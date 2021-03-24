package org.tud.vulnanalysis.model;

import java.net.URI;
import java.util.Objects;

public class ArtifactIdentifier {

    public String GroupId;

    public String ArtifactId;

    public String Version;

    public ArtifactIdentifier(String g, String a, String v){
        this.GroupId = g;
        this.ArtifactId = a;
        this.Version = v;
    }

    public String getCoordinates(){
        return GroupId + ":" + ArtifactId + ":" + Version;
    }

    public URI getMavenCentralPomUri() {
        try {
            return MavenCentralRepository.buildPomFileURI(this);
        } catch(Exception x){
            System.err.println("Failed to build artifact pom url: " + x.getMessage());
            return null;
        }
    }

    @Override
    public String toString(){
        return this.getCoordinates();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtifactIdentifier that = (ArtifactIdentifier) o;
        return GroupId.equals(that.GroupId) &&
                ArtifactId.equals(that.ArtifactId) &&
                Objects.equals(Version, that.Version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(GroupId, ArtifactId, Version);
    }
}
