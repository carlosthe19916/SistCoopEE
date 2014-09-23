package org.softgreen.sistcoop.persona.models;

import org.softgreen.sistcoop.persona.providers.Provider;
import org.softgreen.sistcoop.persona.providers.ProviderFactory;
import org.softgreen.sistcoop.persona.providers.Spi;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class TipoDocumentoSpi implements Spi {

	@Override
	public String getName() {
		return "user";
	}

	@Override
	public Class<? extends Provider> getProviderClass() {
		return TipoDocumentoProvider.class;
	}

	@Override
	public Class<? extends ProviderFactory> getProviderFactoryClass() {
		return TipoDocumentoProviderFactory.class;
	}

}
