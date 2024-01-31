package com.nand.assignment.congestiontaxcalculator.service.validation;

import com.nand.assignment.congestiontaxcalculator.config.VehicleConfig;
import com.nand.assignment.congestiontaxcalculator.exception.InvalidInputException;
import com.nand.assignment.congestiontaxcalculator.model.TaxCalculatorRequest;
import com.nand.assignment.congestiontaxcalculator.utils.AppConstants;
import org.springframework.stereotype.Service;

@Service
public class TaxInputValidator implements IValidationRule {

    private final VehicleConfig vehicleConfig;

    public TaxInputValidator(VehicleConfig vehicleConfig) {
        this.vehicleConfig = vehicleConfig;
    }

    /**
     * Check if input value is not null and valid
     *
     * @param taxInputInfo input for validation
     * @return boolean
     */
    @Override
    public boolean check(TaxCalculatorRequest taxInputInfo) {

        if (taxInputInfo.vehicle() == null) {
            throw new InvalidInputException(AppConstants.EXCEPTION_NULL_VEHICLE);
        }
        if (taxInputInfo.taxDateTime() == null || taxInputInfo.taxDateTime().isEmpty()) {
            throw new InvalidInputException(AppConstants.EXCEPTION_NULL_DATETIME);
        }
        if (vehicleConfig.getVehicles().stream().noneMatch(vehicle -> vehicle.type().equalsIgnoreCase(taxInputInfo.vehicle()))) {
            throw new InvalidInputException(AppConstants.EXCEPTION_INVALID_VEHICLE);
        }
        return false;
    }
}
