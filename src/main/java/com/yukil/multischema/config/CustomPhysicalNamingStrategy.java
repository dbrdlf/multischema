package com.yukil.multischema.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {

    private final AppProperties appProperties;

    @Override
    public Identifier toPhysicalCatalogName(Identifier name,
                                            JdbcEnvironment jdbcEnvironment) {
        return apply(name, jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name,
                                           JdbcEnvironment jdbcEnvironment) {
        return getSchemaName(name, jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name,
                                          JdbcEnvironment jdbcEnvironment) {
        return apply(name, jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name,
                                             JdbcEnvironment jdbcEnvironment) {
        return apply(name, jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name,
                                           JdbcEnvironment jdbcEnvironment) {
        return apply(name, jdbcEnvironment);
    }

    private Identifier getSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        if (name == null) {
            return null;
        }
        StringBuilder builder = null;
        String setting = appProperties.getSetting();
        String logging = appProperties.getLogging();
        if("setting".equals(name.getText())) {
            builder = new StringBuilder(setting);
        }else if("logging".equals(name.getText())) {
            builder = new StringBuilder(logging);
        }else {
            builder = new StringBuilder(name.getText());
        }
        return getIdentifier(builder.toString(), name.isQuoted(), jdbcEnvironment);
    }

    private Identifier apply(Identifier name, JdbcEnvironment jdbcEnvironment) {
        if (name == null) {
            return null;
        }
        StringBuilder builder = null;

        builder = new StringBuilder(name.getText());
        //스키마 명에 있는 .을 _로 강제치환하는걸 막기 위해 replace 삭제
//			StringBuilder builder = new StringBuilder(name.getText().replace('.', '_'));
        for (int i = 1; i < builder.length() - 1; i++) {
            if (isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i),
                    builder.charAt(i + 1))) {
                builder.insert(i++, '_');
            }
        }

        return getIdentifier(builder.toString(), name.isQuoted(), jdbcEnvironment);
    }

    protected Identifier getIdentifier(String name, boolean quoted,
                                       JdbcEnvironment jdbcEnvironment) {
        if (isCaseInsensitive(jdbcEnvironment)) {
            name = name.toLowerCase(Locale.ROOT);
        }
        return new Identifier(name, quoted);
    }

    protected boolean isCaseInsensitive(JdbcEnvironment jdbcEnvironment) {
        return true;
    }

    private boolean isUnderscoreRequired(char before, char current, char after) {
        return Character.isLowerCase(before) && Character.isUpperCase(current)
                && Character.isLowerCase(after);
    }

}
