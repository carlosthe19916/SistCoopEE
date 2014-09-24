/**
 * 
 */
/**
 * @author Huertas
 *
 */
@GenericGenerator(name = "SgGenericGenerator", 
strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", 
parameters = { 
		@Parameter(name = "prefer_sequence_per_entity", value = "true"), 
		@Parameter(name = "optimizer ", value = "pooled") 
		})
package org.softgreen.organizacion.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

