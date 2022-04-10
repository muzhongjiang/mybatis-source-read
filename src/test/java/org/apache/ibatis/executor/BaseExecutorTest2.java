/**
 * Copyright 2009-2020 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.executor;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.domain.blog.Author;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

/**
 *
 * create table author (
 *   id int,
 *   name varchar(16),
 *   password varchar(16),
 *   email varchar(16),
 *   bio varchar(16),
 *   favourite_section varchar(16)
 * );
 *
 *
 *
 * */

class BaseExecutorTest2 extends BaseDataTest {
    protected final Configuration config;
    private static PooledDataSource ds;

    @BeforeAll
    static void setup() throws Exception {
        ds = new PooledDataSource();
        ds.setDriver("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://address=(protocol=tcp)(host=tencent)(port=3306)/test?createDatabaseIfNotExist=true&autoReconnect=true&allowMultiQueries=true&useTimezone=true&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false");
        ds.setUsername("root");
        ds.setPassword("root&password@168");
    }

    BaseExecutorTest2() {
        config = new Configuration();
        config.setLazyLoadingEnabled(true);
        config.setUseGeneratedKeys(false);
        config.setMultipleResultSetsEnabled(true);
        config.setUseColumnLabel(true);
        config.setDefaultStatementTimeout(5000);
        config.setDefaultFetchSize(100);
    }

    @Test
    void shouldInsertNewAuthorWithBeforeAutoKey() throws Exception {

        Executor executor = this.createExecutor(new JdbcTransaction(ds, null, false));
        Configuration configuration = new Configuration();
        String resource = "org/apache/ibatis/builder/AuthorMapper.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());
            builder.parse();

            MappedStatement mappedStatement = configuration.getMappedStatement("selectWithOptions2");

            System.out.println(mappedStatement.getResource());
            BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(null);
            System.out.println(boundSql.getSql());

            List<Author> authors = executor.query(mappedStatement, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
            System.out.println(authors);
        }

    }


    protected Executor createExecutor(Transaction transaction) {
        return new SimpleExecutor(config, transaction);
    }

}
