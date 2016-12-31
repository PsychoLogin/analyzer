package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by jan on 03.12.16.
 */
public class UserBehaviorAnalyserTest {

    UserBehaviorAnalyser cut;

    @Before
    public void init() {
        cut = new UserBehaviorAnalyser();
        cut.emf = mock(EntityManagerFactory.class);
        EntityManager em = mock(EntityManager.class);
        when(cut.emf.createEntityManager()).thenReturn(em);

        List<StaticSessionData> results = new ArrayList<>();
        results.add(new StaticSessionData.Builder()
                .withOperatingSystem("Win10")
                .withLanguage("de")
                .withBrowser("Chrome")
                .withReferrer("facebook.com")
                .build()
        );

        results.add(new StaticSessionData.Builder()
                .withOperatingSystem("Win10")
                .withLanguage("de")
                .withBrowser("IE")
                .withReferrer("google.ch")
                .build()
        );

        results.add(new StaticSessionData.Builder()
                .withOperatingSystem("Win10")
                .withLanguage("de")
                .withBrowser("IE")
                .withReferrer("google.ch")
                .build()
        );

        TypedQuery mockedQuery = mock(TypedQuery.class);
        when(mockedQuery.getResultList()).thenReturn(results);
        when(em.createQuery(Mockito.anyString(), Mockito.any())).thenReturn(mockedQuery);
    }

    @Test
    public void getUserBehavior() {
        UserBehavior user = cut.getUserBehavior("", "");
        Assert.assertEquals(66, (int)user.getBrowserUsage().getUsagesInPercent().get("IE"));
        Assert.assertEquals(100, (int)user.getLanguageUsage().getUsagesInPercent().get("de"));
        Assert.assertEquals(66, (int)user.getReferrer().getUsagesInPercent().get("google.ch"));
        Assert.assertEquals(3, user.getLanguageUsage().getNumberOfLogins());
        Assert.assertEquals(3, user.getBrowserUsage().getNumberOfLogins());
        Assert.assertEquals(3, user.getReferrer().getNumberOfLogins());
    }
}