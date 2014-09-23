package org.softgreen.sistcoop.persona.providers;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public interface Spi {

    public String getName();
    public Class<? extends Provider> getProviderClass();
    public Class<? extends ProviderFactory> getProviderFactoryClass();

}
