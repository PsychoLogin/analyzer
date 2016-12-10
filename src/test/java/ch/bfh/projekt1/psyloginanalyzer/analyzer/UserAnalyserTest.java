package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by jan on 03.12.16.
 */
public class UserAnalyserTest {

    UserAnalyser cut;

    @Before
    public void init() {
        cut = new UserAnalyser();
        cut.em = mock(EntityManager.class);

        List<StaticSessionData> results = new ArrayList<>();
        results.add(new StaticSessionData.Builder()
                .withOperatingSystem("Win10")
                .withLanguage("de")
                .withBrowser("Chrome")
                .build()
        );

        results.add(new StaticSessionData.Builder()
                .withOperatingSystem("Win10")
                .withLanguage("de")
                .withBrowser("IE")
                .build()
        );

        results.add(new StaticSessionData.Builder()
                .withOperatingSystem("Win10")
                .withLanguage("de")
                .withBrowser("IE")
                .build()
        );

        TypedQuery mockedQuery = mock(TypedQuery.class);
        when(mockedQuery.getResultList()).thenReturn(results);
        when(cut.em.createQuery(Mockito.anyString(), Mockito.any())).thenReturn(mockedQuery);
    }

    @Test
    public void getUserBehavior() {
        UserBehavior user = cut.getUser("", "");
        Assert.assertEquals("de", user.getPreferredLanguage());
        Assert.assertEquals("IE", user.getPreferredBrowser());
    }
}