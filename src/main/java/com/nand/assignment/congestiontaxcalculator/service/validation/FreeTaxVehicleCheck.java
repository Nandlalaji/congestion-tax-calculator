package com.nand.assignment.congestiontaxcalculator.service.validation;

import com.nand.assignment.congestiontaxcalculator.config.VehicleConfig;
import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;
import org.springframework.stereotype.Service;

/**
 * This class deals with checking if provided vehicle is tax Free or not
 *
 * @author nandlalajisingh@gmail.com
 * @since 1.0
 */
@Service
public class FreeTaxVehicleCheck implements IValidationRule {

    private final VehicleConfig vehicleConfig;

    public FreeTaxVehicleCheck(VehicleConfig vehicleConfig) {
        this.vehicleConfig = vehicleConfig;
    }

    /**
     * @param taxInputInfo input for validating type
     * @return boolean value
     */
    @Override
    public boolean check(TaxCalculatorRequest taxInputInfo) {
        var taxFreeVehicle = vehicleConfig.getVehicles().stream()
                .filter(v -> v.type().equals(taxInputInfo.vehicle()) && v.isTaxFree()).findFirst();
        return taxFreeVehicle.isPresent();
    }
}
