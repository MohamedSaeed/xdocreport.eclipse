/**
 * Copyright (C) 2011 Angelo Zerr <angelo.zerr@gmail.com> and Pascal Leclercq <pascal.leclercq@gmail.com>
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.opensagres.xdocreport.template.registry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.opensagres.xdocreport.core.registry.AbstractRegistry;
import fr.opensagres.xdocreport.template.ITemplateEngine;
import fr.opensagres.xdocreport.template.discovery.ITemplateEngineDiscovery;

/**
 * Template engien registry which store instance of {@link ITemplateEngine} for
 * template engine kind (Freemarker, Velocity) and document kind (odt, docx..)
 * 
 */
public class TemplateEngineRegistry extends AbstractRegistry<ITemplateEngineDiscovery> implements
		Serializable {

	private static final long serialVersionUID = -7686229087661483932L;
	private static final String TEMPLATE_DISCOVERY_PROPERTIES = "template-discovery.properties";
	private static final TemplateEngineRegistry INSTANCE = new TemplateEngineRegistry();

	//

	private Map<String, ITemplateEngine> templateEnginesyCache = new HashMap<String, ITemplateEngine>();
	private Map<String, ITemplateEngineDiscovery> templateEnginesDiscoveryCache = new HashMap<String, ITemplateEngineDiscovery>();
	private ITemplateEngine defaultTemplateEngine;
	private Collection<String> templateEngineKinds = new ArrayList<String>();

	public static TemplateEngineRegistry getRegistry() {
		return INSTANCE;
	}

	public TemplateEngineRegistry() {
		super(ITemplateEngineDiscovery.class);
	}

	
	
	
	@Override
	protected boolean registerInstance(ITemplateEngineDiscovery instance) {
		
			ITemplateEngineDiscovery discovery = (ITemplateEngineDiscovery) instance;
			templateEnginesDiscoveryCache.put(discovery.getId(), discovery);
			ITemplateEngine templateEngine = discovery.createTemplateEngine();
			register(templateEngine);
			return true;
		
		
	}

	/*@Override
	protected void initializeIfNeeded() {
		// TODO Auto-generated method stub
		super.initializeIfNeeded();
		
		Iterator<ITemplateEngineDiscovery> converterDiscoveries = ServiceRegistry.lookupProviders(ITemplateEngineDiscovery.class,getClass().getClassLoader());
		
		while (converterDiscoveries.hasNext()) {
			ITemplateEngineDiscovery instance =  converterDiscoveries.next();
			
		boolean result=	registerInstance(instance);

			
		}
	}*/
	private void register(ITemplateEngine templateEngine) {
		//templateEnginesCache.put(templateEngine.getId(), templateEngine);
		templateEnginesyCache.put(templateEngine.getKind(), templateEngine);
		//register(templateEngine, (String) null);
	}
	

	public boolean isDefault(ITemplateEngine templateEngine) {
		initializeIfNeeded();
		if (templateEngine == null) {
			return false;
		}
		return templateEngine.equals(defaultTemplateEngine);
	}

	public ITemplateEngine getDefaultTemplateEngine() {
		initializeIfNeeded();
		return defaultTemplateEngine;
	}

	public void setDefaultTemplateEngine(ITemplateEngine defaultTemplateEngine) {
		this.defaultTemplateEngine = defaultTemplateEngine;
	}

	

//	public Collection<ITemplateEngine> getTemplateEngines() {
//		initializeIfNeeded();
//		return templateEnginesCache.values();
//	}

	public Collection<String> getTemplateEngineKinds() {
		initializeIfNeeded();
		return templateEnginesyCache.keySet();
		//return templateEngineKinds;
	}

	public Map<String, ITemplateEngineDiscovery> getTemplateEnginesDiscoveryCache() {
		return templateEnginesDiscoveryCache;
	}
	@Override
	protected void doDispose() {
	//	this.templateEnginesCache.clear();
		//this.templateEnginesInitializerDiscoveryCache.clear();
		this.templateEnginesDiscoveryCache.clear();
		this.defaultTemplateEngine = null;
		this.templateEngineKinds.clear();

	}
}
