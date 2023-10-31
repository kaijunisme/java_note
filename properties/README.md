# Properties

平時開發專案時，經常需要設定參數在`.yaml`或`.properties`檔案裡；使用Spring 框架時，有相關的Annotation 協助取得參數，而若遇到舊專案或無框架專案時，則需要自己撰寫相關邏輯。

---

## 設定資源檔位置

在`pom.xml`中的`<build>`標籤新增`<resource>`標籤即可設定資源檔位置，其`<directory>`標籤就是指定檔案路徑。

不過預設會是路徑底下所有檔案，可以另外使用`<include>`或`<exclude>`指定需要哪些檔案或不需要哪些檔案。

```xml
<build>
    <resources>
        <resource>
            <directory>${basedir}/src/main/resources</directory>
            <filtering>true</filtering>
            <includes>
                <include>**/application.yml</include>
                <include>**/application.yaml</include>
                <include>**/application.properties</include>
            </includes>
        </resource>
    </resources>
    <testResources>
        <testResource>
            <directory>${basedir}/src/test/resources</directory>
            <filtering>true</filtering>
        </testResource>
    </testResources>
</build>
```

---

## Profile

在系統開發流程中，通常會根據階段分成不同的執行環境，除了設定`environment variables`指定環境變數外，還可以在`pom.xml`設定Profile。

在`pom.xml`中新增`<profiles>`標籤，當中可以設定不同的`<profile>`選項。

```xml
<profiles>
    <profile>
        <id>dev</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <profileActive>dev</profileActive>
        </properties>
    </profile>
    <profile>
        <id>sit</id>
        <properties>
            <profileActive>sit</profileActive>
        </properties>
    </profile>
</profiles>
```

---

## 設定Properties 和YAML 檔

Properties 和YAML 檔各設定application 和application-sit 兩個檔案，其內容如下：

> ### application.properties

```properties
name.project=properties
name.resource-file=application.properties
active.profile=@profileActive@
```

> ### application-sit.properties

```properties
name.resource-file=application-sit.properties
```

> ### application.yml

```yaml
name:
  project: properties
  resource-file: application.yml
active:
  profile: '@profileActive@'
```

> ### application-sit.yml

```yaml
name:
  resource-file: application-sit.yml
```

---

## Properties

取得Properties 設定參數較為簡單，讀檔後使用`Properties`載入`InputStream`，使用`Properties`的`getProperties()`方法指定Key 值即可取用參數。

> ### PropertiesApplication (Properties 相關片段)

```java
package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

public class PropertiesApplication {

    private static final String defaultFileName = "/application.%s";
    private static final String activeFileName = "/application-%s.%s";

    public static void main(String[] args) throws IOException {
        readPropertiesFile();
    }

    private static InputStream readFile(String fileName) {
        return PropertiesApplication.class.getResourceAsStream(fileName);
    }

    private static void readPropertiesFile() throws IOException {
        // 讀取預設參數檔
        InputStream defaultProperties = readFile(format(defaultFileName, "properties"));

        Properties properties = new Properties();
        properties.load(defaultProperties);

        // 讀取指定環境參數檔
        String activeProfile = properties.getProperty("active.profile");
        if (null != activeProfile && !activeProfile.isEmpty()) {
            InputStream activeProperties = readFile(format(activeFileName, activeProfile, "properties"));
            if (null != activeProperties) {
                properties.load(activeProperties);
            }
        }
    }
}
```

---

## YAML

取得YAML 設定參數則較為複雜，需要引入其他依賴並進行一些操作賦值。

> ### Dependency

```xml
<dependency>
    <groupId>org.yaml</groupId>
    <artifactId>snakeyaml</artifactId>
    <version>1.33</version>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-yaml</artifactId>
    <version>2.14.2</version>
</dependency>
```

> ### PropertiesApplication (Properties 相關片段)

```java
package com.example;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class PropertiesApplication {

    private static final String defaultFileName = "/application.%s";
    private static final String activeFileName = "/application-%s.%s";

    public static void main(String[] args) throws IOException {
        readYamlFile();
    }

    private static InputStream readFile(String fileName) {
        return PropertiesApplication.class.getResourceAsStream(fileName);
    }

    private static void readYamlFile() {
        // 讀取預設參數檔
        InputStream yamlStream = readFile(format(defaultFileName, "yml"));

        Yaml yaml = new Yaml();
        Map<String, Object> defaultYaml = yaml.load(yamlStream);

        // 讀取指定環境參數檔
        Map<String, Object> active = (HashMap) defaultYaml.get("active");
        String profile = (String) active.get("profile");
        if (null != profile && !profile.isEmpty()) {
            InputStream activeProperties = readFile(format(activeFileName, profile, "yml"));
            if (null != activeProperties) {
                Map<String, Object> activeYaml = yaml.load(activeProperties);
                refreshMap(defaultYaml, activeYaml);
            }
        }
    }

    /**
     * 更新Map key-value 內容，若無key 值新增，反之則取代
     *
     * @param target
     * @param source
     */
    private static void refreshMap(Map<String, Object> target, Map<String, Object> source) {
        source.forEach((key, value) -> {
            if (value instanceof Map) {
                refreshMap((Map) target.get(key), (Map) value);
            } else {
                target.put(key, value);
            }
        });
    }
}
```

---
