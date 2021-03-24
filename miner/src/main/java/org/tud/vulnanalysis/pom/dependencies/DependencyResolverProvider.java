package org.tud.vulnanalysis.pom.dependencies;

import org.tud.vulnanalysis.model.ArtifactIdentifier;

import java.io.File;
import java.lang.reflect.Constructor;

public class DependencyResolverProvider {

    private static DependencyResolverProvider theInstance;
    private Class<? extends DependencyResolver> resolverType;
    private Class<? extends DependencyResolver> backupResolverType;

    private DependencyResolverProvider(){
        // The default resolver
        this.registerResolverType(MvnPluginDependencyResolver.class);
        this.backupResolverType = null;
    }

    public DependencyResolver buildResolver(File pomFile, ArtifactIdentifier identifier){
        try {
            Constructor<? extends DependencyResolver> c = resolverType.getConstructor(File.class, ArtifactIdentifier.class);
            return c.newInstance(pomFile, identifier);
        } catch (Exception x){
            return null;
        }
    }

    public DependencyResolver buildBackupResolver(File pomFile, ArtifactIdentifier identifier){
        if(this.backupResolverType == null)
            return null;

        try {
            Constructor<? extends DependencyResolver> c =
                    backupResolverType.getConstructor(File.class, ArtifactIdentifier.class);
            return c.newInstance(pomFile, identifier);
        } catch (Exception x){
            return null;
        }
    }

    public void registerResolverType(Class<? extends DependencyResolver> resolver){
        if(resolver == null){
            throw new IllegalArgumentException("Primary resolver type cannot be null.");
        }
        this.resolverType = resolver;
    }

    public void registerBackupResolverType(Class<? extends DependencyResolver> resolver){
        this.backupResolverType = resolver;
    }

    public static DependencyResolverProvider getInstance() {
        if(theInstance == null)
            theInstance = new DependencyResolverProvider();

        return theInstance;
    }
}