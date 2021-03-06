package com.excilys.cdb.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TagLib qui permet de créer un lien avec plusieurs paramètres.
 * @author martin
 *
 */
public class LinkLib extends SimpleTagSupport {

    /**
     * La recherche voulue.
     */
    private String search;

    /**
     * La page voulue.
     */
    private int page;

    /**
     * Le nombre d'élement par page.
     */
    private int count;

    /**
     * L'URI de redirection sans paramètre.
     */
    private String uri;

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkLib.class);

    /**
     * Affiche les différents éléments de pagination.
     *
     */
    @Override
    public void doTag() {
        JspWriter writer = getJspContext().getOut();
        try {
            writer.print("\"" + uri + "?search=" + search + "&page=" + page + "&resultPerPage=" + count + "\"");
        } catch (IOException e) {
            LOGGER.debug("LINK LIB ERROR " + e.getMessage());
        }
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
