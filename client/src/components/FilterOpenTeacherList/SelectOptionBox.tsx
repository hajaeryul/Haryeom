import { ReactNode } from 'react';
import styled from 'styled-components';

interface SelectOptionBoxProps {
    children: ReactNode;
    name: string;
    optionValues: string[];
    handleSelectOption: (name: string, value: string) => void;
    isSelected: (name: string, value: string) => boolean;
}

const SelectOptionBox = ({
    children,
    name,
    optionValues,
    handleSelectOption,
    isSelected,
}: SelectOptionBoxProps) => {
    return (
        <StyledSelectOptionValuBox>
            {children}
            <Options>
                {optionValues.map((optionValue: string, index: number) => {
                    return (
                        <Option
                            key={`filterOptions_${index}`}
                            onClick={() => handleSelectOption(name, optionValue)}
                            selected={isSelected(name, optionValue)}
                        >
                            {optionValue}
                        </Option>
                    );
                })}
            </Options>
        </StyledSelectOptionValuBox>
    );
};

const StyledSelectOptionValuBox = styled.div`
    min-width: 100px;
    padding: 1em;
    background-color: white;
    border-radius: 0.6em;

    border: 1px solid ${({ theme }) => theme.BORDER_LIGHT};
    box-shadow: 0px 0px 20px rgba(105, 105, 105, 0.25);
`;

const Options = styled.div`
    max-height: 30em;
    height: 100%;
    white-space: nowrap;
    display: flex;
    flex-direction: column;
    overflow: scroll;
`;

const Option = styled.button<{ selected: boolean }>`
    margin: 0.3em 0;
    padding: 0.4em;
    border-radius: 0.4em;
    text-align: left;
    ${({ selected, theme }) => selected && ` background-color: ${theme.PRIMARY_LIGHT}`};

    &:hover {
        background-color: ${({ theme }) => theme.PRIMARY_LIGHT};
        transition: all 0.3s;
    }
`;

export default SelectOptionBox;
