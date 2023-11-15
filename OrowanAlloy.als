/*
abstract sig User { }

sig Everyone extends User{

}

sig Worker extends User{
}

sig ProcessEngineer extends User{
}
*/



//module login
module HMI

sig Email {}
sig Password {}
sig StandID {}

sig Orowan {
	total_computes: Int,
	measure: set Te_in & Te_out & Th_in & Th_out & WR_Diam & ForwardSlip & RollingForce
}
sig FrictionCoefficientFactor {}
sig UserRights{}
sig ApplicationSetting{
	enable: set Stand,
	disable: set Stand,
	change: some Level2InputRange

}
sig Stand{}
sig Level2InputRange {}
sig Database{}
abstract sig Data { values: Int }
//sig WorkRollDegradation{}
sig Information{}
sig Remain_Life_Roll{}
sig Prevention_system{
	detect: set Potential_threading_refusal,
	provide: lone Alert
}

sig Wear_workroll in Information{}
sig Tear_workrokk in Information{}
sig Effective_lubrication in Information{}

sig Threading_Refusal{}
abstract sig lubrication_indicator{
	provide: some Information,
	recommend: set Preset_Lubrication_System
}

sig Potential_threading_refusal{}
sig Alert{}
sig Preset_Lubrication_System{}

sig Rolling_process{}
sig Occurence_defect{}

sig ReadCSV{
	store: one Database
}

sig Te_in in ReadCSV{} //Thickness in
sig Te_out in ReadCSV{} //Thickness in
sig Th_in in ReadCSV{}  //Tension in
sig Th_out in ReadCSV{} //Thickness out 
sig WR_Diam in ReadCSV{} // Working Roll Diameter 
sig ForwardSlip in ReadCSV{} //fs
sig RollingForce in ReadCSV{} // F

sig Torque in Preset_Model{}
sig Roll_Force in Preset_Model{}
sig Forward_slip in Preset_Model{}

abstract sig User {
   	login: some Login,
    	logout: some Logout
}

abstract sig Login {
    	email: one Email,
    	password: one Password,
    	everyone: some User,
}

abstract sig Logout {
   	everyone: some User,
}

abstract sig Worker extends User{
	id: one StandID,
	compute: some Orowan, 
	determine: FrictionCoefficientFactor
}

abstract sig ProcessEngineer extends User{
	add: set UserRights,
	remove: set UserRights,
	update: set UserRights,
	change: set ApplicationSetting
}



abstract sig FrictionCoefficientCalculation{
	using: set Orowan,
	develop: set WorkRoll_indicator,
	establish: set Threading_Refusal,
	improve: set Prevention_system,
	find: lone lubrication_indicator,
	indentify: some Preset_Model
}

abstract sig WorkRoll_indicator{
	provide: set Information,
	predict: set Remain_Life_Roll
}



abstract sig Preset_Model{
	optimize: one Rolling_process,
	reduce: some Occurence_defect
}


// function to compute Orowan model 200ms
fun sum_200ms: Int { sum d: Data | d.values }
fact { Orowan.total_computes = sum_200ms }



fact {
    all u:User | u.login.everyone = u
}

assert a {
    all l:Login | some l.everyone
    all u:User | some u.login.email
    all u:User | u.login.everyone = u
}

check a for 3



/*
one sig Restaurant { total_sales: Int }
abstract sig Meal { sales: Int }
one sig Breakfast, Lunch, Dinner in Meal {}

// Each meal only falls in one category
fact { disj[Breakfast, Lunch, Dinner] }
// Every meal is in some category
fact { Breakfast + Lunch + Dinner = Meal }

// Keep the numbers small because the max alloy int is 7
fact { all m: Meal | m.sales <= 4 }
// Negative sales don't make sense
fact { all m: Meal | m.sales >= 0 }

fun sum_sales: Int { sum m: Meal | m.sales }
fact { Restaurant.total_sales = sum_sales }

check { (all m: Meal | m.sales = 1) => Restaurant.total_sales = 3 }
*/
