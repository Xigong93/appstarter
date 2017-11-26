package com.pokercc.appstarter;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.BuckManifestFactory;
import org.robolectric.internal.GradleManifestFactory;
import org.robolectric.internal.ManifestFactory;
import org.robolectric.internal.ManifestIdentifier;
import org.robolectric.internal.MavenManifestFactory;
import org.robolectric.res.Fs;
import org.robolectric.res.FsFile;
import org.robolectric.util.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;

/**
 * Created by Cisco on 2017/11/25.
 */

public class CustomManifestLocationTestRunner extends RobolectricTestRunner {
    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory by default. Use the {@link Config} annotation to configure.
     *
     * @param testClass the test class to be run
     * @throws InitializationError if junit says so
     */
    public CustomManifestLocationTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    protected ManifestFactory getManifestFactory(Config config) {


        Properties buildSystemApiProperties = getBuildSystemApiProperties();
        if (buildSystemApiProperties != null) {
            return new CustomManifestFactory(buildSystemApiProperties);
        }

        Class<?> buildConstants = config.constants();
        //noinspection ConstantConditions
        if (BuckManifestFactory.isBuck()) {
            return new BuckManifestFactory();
        } else if (buildConstants != null && buildConstants != Void.class) {
            return new GradleManifestFactory();
        } else {
            return new MavenManifestFactory();
        }
    }

    Properties getBuildSystemApiProperties() {
        InputStream resourceAsStream = getClass().getResourceAsStream("/com/android/tools/test_config.properties");
        if (resourceAsStream == null) {
            return null;
        }

        try {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            return properties;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                throw new RuntimeException("couldn't close test_config.properties", e);
            }
        }
    }

    private static class CustomManifestFactory implements ManifestFactory {

        private Properties properties;

        public CustomManifestFactory(Properties properties) {
            this.properties = properties;
        }

        @Override
        public ManifestIdentifier identify(Config config) {

            FsFile resourcesDir = getFsFileFromPath(properties.getProperty("android_merged_resources"));
            FsFile assetsDir = getFsFileFromPath(properties.getProperty("android_merged_assets"));
            String packageName = properties.getProperty("android_custom_package");

            FsFile manifestFile = null;
            if (Config.NONE.equals(config.manifest())) {
                Logger.info("@Config(manifest = Config.NONE) specified while using Build System API, ignoring");
            } else if (!Config.DEFAULT_MANIFEST_NAME.equals(config.manifest())) {
                manifestFile = Fs.newFile(new File(".").getAbsolutePath() + config.manifest());
                Logger.info("manifestPath:" + manifestFile.getPath());
            }

            if (!Config.DEFAULT_RES_FOLDER.equals(config.resourceDir())) {
                resourcesDir = resolveFile(config.resourceDir());
            }

            if (!Config.DEFAULT_ASSET_FOLDER.equals(config.assetDir())) {
                assetsDir = resolveFile(config.assetDir());
            }

            if (!Config.DEFAULT_PACKAGE_NAME.equals(config.packageName())) {
                packageName = config.packageName();
            }

            List<FsFile> libraryDirs = emptyList();
            if (config.libraries().length > 0) {
                Logger.info("@Config(libraries) specified while using Build System API, ignoring");
            }

            return new ManifestIdentifier(manifestFile, resourcesDir, assetsDir, packageName, libraryDirs);
        }

        private FsFile resolveFile(String manifestConfig) {
            URL manifestUrl = getClass().getClassLoader().getResource(manifestConfig);
            if (manifestUrl == null) {
                throw new IllegalArgumentException("couldn't find '" + manifestConfig + "'");
            } else {
                return Fs.fromURL(manifestUrl);
            }
        }

        private FsFile getFsFileFromPath(String property) {
            if (property.startsWith("jar")) {
                try {
                    URL url = new URL(property);
                    return Fs.fromURL(url);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                return Fs.fileFromPath(property);
            }
        }
    }
}
